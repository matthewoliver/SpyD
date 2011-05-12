package org.hibernate.tool.stat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableCellRenderer;

import org.hibernate.stat.Statistics;

/**
 * Very rudimentary statistics browser.
 * 
 * Usage:
 * new StatisticsBrowser().showStatistics(getSessions().getStatistics(), shouldBlock);
 * 
 * @author max
 *
 */
public class StatisticsBrowser {
	
	/**
	 * 
	 * @param stats a Statistics instance obtained from a SessionFactory
	 * @param shouldBlock decides if the ui will be modal or not.
	 */
	public void showStatistics(Statistics stats, boolean shouldBlock) {
		
		JDialog main = new JDialog((JFrame)null, "Statistics browser");
		
		main.getContentPane().setLayout(new BorderLayout());
		
		final StatisticsTreeModel statisticsTreeModel = new StatisticsTreeModel(stats);
		JTree tree = new JTree(statisticsTreeModel);
		tree.setCellRenderer( new StatisticsCellRenderer() );
		ToolTipManager.sharedInstance().registerComponent(tree);
		
		JScrollPane treePane = new JScrollPane(tree);
		
		final JTable table = new JTable() {
			public TableCellRenderer getDefaultRenderer(Class columnClass) {
				TableCellRenderer defaultRenderer = super.getDefaultRenderer( columnClass );
				
				if(defaultRenderer==null) {
					return super.getDefaultRenderer( Object.class );
				} else {
					return defaultRenderer;
				}
			}
		};
		
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.getViewport().setBackground( table.getBackground() );
		final BeanTableModel beanTableModel = new BeanTableModel(Collections.EMPTY_LIST, Object.class);
		table.setModel( beanTableModel );
		
		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, tablePane);
		pane.setContinuousLayout( true );
		
		main.getContentPane().add(pane, BorderLayout.CENTER);
		
		tree.addTreeSelectionListener( new TreeSelectionListener() {
		
			public void valueChanged(TreeSelectionEvent e) {
				Object lastPathComponent = e.getPath().getLastPathComponent();
				List l = new ArrayList();
				if(statisticsTreeModel.isContainer( lastPathComponent )) {
					int childCount = statisticsTreeModel.getChildCount( lastPathComponent );
					
					Class cl = Object.class;
					for (int i = 0; i < childCount; i++) {
						Object v = statisticsTreeModel.getChild( lastPathComponent, i );
						if(v!=null) cl = v.getClass();
						l.add(v);
					}
					table.setModel( new BeanTableModel(l, cl) );	
				} else {
					l.add( lastPathComponent );
					table.setModel( new BeanTableModel(l, lastPathComponent.getClass()) );
				}
				
				//table.doLayout();
				
			}
		
		} );
		
		
		main.getContentPane().setSize(new Dimension(640,480));
		main.pack();
		main.setModal(shouldBlock);
		main.setVisible(true);
	}
	
	
	
	

}
