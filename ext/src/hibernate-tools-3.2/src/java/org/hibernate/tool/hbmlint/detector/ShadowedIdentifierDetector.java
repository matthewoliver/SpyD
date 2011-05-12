package org.hibernate.tool.hbmlint.detector;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.tool.hbmlint.Issue;
import org.hibernate.tool.hbmlint.IssueCollector;

public class ShadowedIdentifierDetector extends EntityModelDetector {
	
	public String getName() {
		return "shadow-id";
	}
	
	public void visitProperty(Configuration configuration, PersistentClass clazz, Property property, IssueCollector collector) {
		if(property.getName().equals("id")) {
			if (property != property.getPersistentClass().getIdentifierProperty()) {
				collector.reportIssue(new Issue("ID_SHADOWED", Issue.LOW_PRIORITY, property.getPersistentClass().getEntityName() + " has a normal property named 'id'. This can cause issues since HQL queries will always interpret 'id' as the identifier and not the concrete property"));
			}
		}
	}
}
