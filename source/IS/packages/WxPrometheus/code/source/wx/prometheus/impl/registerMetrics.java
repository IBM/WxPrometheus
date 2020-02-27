package wx.prometheus.impl;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;
import com.wm.app.b2b.server.ServerAPI;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataUtil;
// --- <<IS-END-IMPORTS>> ---

public final class registerMetrics

{
	// ---( internal utility methods )---

	final static registerMetrics _instance = new registerMetrics();

	static registerMetrics _newInstance() { return new registerMetrics(); }

	static registerMetrics _cast(Object o) { return (registerMetrics)o; }

	// ---( server methods )---




	public static final void getPackageList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getPackageList)>> ---
		// @sigtype java 3.5
		// [o] record:1:required packages
		// [o] - field:0:required name
		// [o] - field:0:required enabled
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		String[] packageList = ServerAPI.getEnabledPackages();
		
		// packages
		IData[]	packages = new IData[packageList.length];
		for( int i=0; i<packages.length; i++ ) {
			packages[i] = IDataFactory.create();
			IDataCursor packagesCursor = packages[i].getCursor();
			IDataUtil.put( packagesCursor, "name", packageList[i] );
			IDataUtil.put( packagesCursor, "enabled", "true" );
			packagesCursor.destroy();
		}
		IDataUtil.put( pipelineCursor, "packages", packages );
		pipelineCursor.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void loadPrometheusConfigForPackage (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(loadPrometheusConfigForPackage)>> ---
		// @sigtype java 3.5
		// [i] field:0:required packageName
		// [o] recref:0:required packageConfig wx.prometheus.impl.documents:packageConfig
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String packageName = IDataUtil.getString(pipelineCursor, "packageName");
		pipelineCursor.destroy();
		
		if (packageName == null) {
			return;
		}
		File configDir = ServerAPI.getPackageConfigDir(packageName);
		if (!configDir.exists()) {
			return;
		}
		String[] configFiles = configDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File paramFile, String paramString) {
				return paramString.equalsIgnoreCase(CONFIG_FILE_NAME);
			}
		});
		for (String configFileName : configFiles) {
			File configFile = new File(configDir, configFileName);
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(configFile));
				logger.info("Found " + CONFIG_FILE_NAME + " file in config dir for package " + packageName);
				String registerForPrometheus =  props.getProperty("registerForPrometheus", "false");
				if( !"true".equals(registerForPrometheus) ) {
					registerForPrometheus = "false";
				}
				String metricsServiceFqn =  props.getProperty("metricsServiceFqn");
				
				IData	packageConfig = IDataFactory.create();
				IDataCursor packageConfigCursor = packageConfig.getCursor();
				IDataUtil.put( packageConfigCursor, "registerForPrometheus", registerForPrometheus );
				IDataUtil.put( packageConfigCursor, "packageName", packageName );
				IDataUtil.put( packageConfigCursor, "metricsServiceFqn", metricsServiceFqn );
				packageConfigCursor.destroy();
				IDataUtil.put( pipelineCursor, "packageConfig", packageConfig );
		
			} catch( IOException e ) {
				logger.error("Could not load config file '" + CONFIG_FILE_NAME + "' for package " + packageName + ": " + e);
			}
			
			pipelineCursor.destroy();
		}
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("wx.prometheus.config");
	static String CONFIG_FILE_NAME = "wxprometheus.properties";
	// --- <<IS-END-SHARED>> ---
}

