package com.tinkerpop.rexster.config;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.impls.neo4j.Neo4jGraph;
import com.tinkerpop.rexster.Tokens;

public class Neo4jGraphConfiguration implements GraphConfiguration {

	@Override
	public Graph configureGraphInstance(Configuration properties) throws GraphConfigurationException {
		
		try {
	        String graphFile = properties.getString(Tokens.REXSTER_GRAPH_FILE);
	        
	        // get the <properties> section of the xml configuration
	        HierarchicalConfiguration graphSectionConfig = (HierarchicalConfiguration) properties;
	        SubnodeConfiguration neo4jSpecificConfiguration = graphSectionConfig.configurationAt(Tokens.REXSTER_GRAPH_PROPERTIES);
	
	        // properties to initialize the neo4j instance.
	        HashMap<String, String> neo4jProperties = new HashMap<String, String>();
	
	        // read the properties from the xml file and convert them to properties
	        // to be injected into neo4j.
	        Iterator<String> neo4jSpecificConfigurationKeys = neo4jSpecificConfiguration.getKeys();
	        while (neo4jSpecificConfigurationKeys.hasNext()) {
	            String key = neo4jSpecificConfigurationKeys.next();
	            neo4jProperties.put(key, neo4jSpecificConfiguration.getString(key));
	        }
	
	        return new Neo4jGraph(graphFile, neo4jProperties);
		} catch (Exception ex){
			throw new GraphConfigurationException(ex);
		}
	}

}