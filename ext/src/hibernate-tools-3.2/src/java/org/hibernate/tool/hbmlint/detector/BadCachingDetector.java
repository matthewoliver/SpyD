package org.hibernate.tool.hbmlint.detector;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Value;
import org.hibernate.tool.hbm2x.visitor.EntityNameFromValueVisitor;
import org.hibernate.tool.hbmlint.Issue;
import org.hibernate.tool.hbmlint.IssueCollector;

public class BadCachingDetector extends EntityModelDetector {
	
	public String getName() {
		return "cache";
	}
	public void visitProperty(Configuration configuration, PersistentClass clazz, Property property, IssueCollector collector) {
		Value value = property.getValue();
		
		if(value instanceof Collection) {
			Collection col = (Collection) value;
			if(col.getCacheConcurrencyStrategy()!=null) { // caching is enabled
				if (!col.getElement().isSimpleValue()) {
					String entityName = (String) col.getElement().accept( new EntityNameFromValueVisitor() );

					if(entityName!=null) {
						PersistentClass classMapping = configuration.getClassMapping( entityName );
						if(classMapping.getCacheConcurrencyStrategy()==null) {
							collector.reportIssue( new Issue("CACHE_COLLECTION_NONCACHABLE_TARGET", Issue.HIGH_PRIORITY, "Entity '" + classMapping.getEntityName() +"' is referenced from the cache-enabled collection '" + col.getRole() + "' without the entity being cachable"));
						}
					}
				}
			}
		}	
	}
}
