package wx.prometheus.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Arrays;
import java.util.HashMap;
import com.wm.data.IData;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
// --- <<IS-END-IMPORTS>> ---

public final class registerMetrics

{
	// ---( internal utility methods )---

	final static registerMetrics _instance = new registerMetrics();

	static registerMetrics _newInstance() { return new registerMetrics(); }

	static registerMetrics _cast(Object o) { return (registerMetrics)o; }

	// ---( server methods )---




	public static final void countRegisteredMetricsProviders (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(countRegisteredMetricsProviders)>> ---
		// @sigtype java 3.5
		// [o] field:0:required nrRegisteredMetricsProviders
		IDataCursor pipelineCursor = pipeline.getCursor();
		int nrRegisteredMetricsProviders = 0;
		if( registeredMetrics != null ) {
			nrRegisteredMetricsProviders = registeredMetrics.size();
		}
		IDataUtil.put( pipelineCursor, "nrRegisteredMetricsProviders", Integer.toString(nrRegisteredMetricsProviders) );
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getRegisteredMetrics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRegisteredMetrics)>> ---
		// @specification wx.prometheus.pub.documents:prometheusMetric
		// @sigtype java 3.5
		java.util.List<IData> textMetricsList = new java.util.ArrayList<IData>();
		java.util.List<String> textMetricsStringList = new java.util.ArrayList<String>();
		java.util.List<Object> metricsBytesList = new java.util.ArrayList<Object>();
		if( registeredMetrics == null ) {
			registeredMetrics = new java.util.HashMap<String, String[]>();
			try{
				Service.doInvoke( "wx.prometheus.impl.registerMetrics", "loadConfig", IDataFactory.create() );
			}catch( Exception e){
				logger.error("Could not load config from service wx.prometheus.impl.registerMetrics:loadConfig: " + e);
			}
		}
		if( logger.getLevel() == Level.TRACE ) {
			logger.trace("Getting registered metrics. Number of registered metrics services: " + registeredMetrics.size());
		}		
		String[] metric;
		for( String service : registeredMetrics.keySet() ) {
			metric = registeredMetrics.get(service);
			if( metric == null || metric.length != 2 ) {
				continue;
			}
			IData 	output = IDataFactory.create();
			try{
				output = Service.doInvoke( metric[0], metric[1], IDataFactory.create() );
				IData[] textMetrics = IDataUtil.getIDataArray(output.getCursor(), "textMetrics");
				String[] textMetricsString = IDataUtil.getStringArray(output.getCursor(), "textMetricsString");
				Object[] metricsBytes = IDataUtil.getObjectArray(output.getCursor(), "metricsBytes");
				if ( null != textMetrics ) textMetricsList.addAll(Arrays.asList(textMetrics));
				
				if ( null != textMetricsString ) textMetricsStringList.addAll(Arrays.asList(textMetricsString));
				if ( null != metricsBytes ) metricsBytesList.addAll(Arrays.asList( metricsBytes));
				
			} catch( Exception e){
				e.printStackTrace();
				logger.error("WxPrometheus: Exception occurred when invoking registered metrics service: " + e);
				
			}
		}
		if (textMetricsList.size() > 0) IDataUtil.put(pipeline.getCursor(), "textMetrics", textMetricsList.toArray(new IData[textMetricsList.size()]));
		if (textMetricsStringList.size() > 0) IDataUtil.put(pipeline.getCursor(), "textMetricsString", textMetricsStringList.toArray(new String[textMetricsStringList.size()]));
		if (metricsBytesList.size() > 0) IDataUtil.put(pipeline.getCursor(), "metricsBytes", metricsBytesList.toArray( new Object[metricsBytesList.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void registerMetrics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(registerMetrics)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional ifcname
		// [i] field:0:optional svcname
		// [i] field:0:optional metricsServiceFqn
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	ifcname = IDataUtil.getString( pipelineCursor, "ifcname" );
		String	svcname = IDataUtil.getString( pipelineCursor, "svcname" );
		String	metricsServiceFqn = IDataUtil.getString( pipelineCursor, "metricsServiceFqn" );
		pipelineCursor.destroy();
		
		if( (ifcname == null || "".equals(ifcname) || svcname == null || "".equals(svcname) ) && (metricsServiceFqn == null || "".equals(metricsServiceFqn) )) {
			throw new ServiceException("Provide the folder and service name for the service implementing the wx.prometheus.pub.documents:prometheusMetric specification");
		}
		
		if( metricsServiceFqn != null && !"".equals(metricsServiceFqn) && metricsServiceFqn.contains(":") ) {
			String[] service = metricsServiceFqn.split(":");
			ifcname = service[0];
			svcname = service[1];
		}
		logger.debug("Added service " + ifcname + ":" + svcname + " to the list of registered metrics services.");
		registeredMetrics.put(metricsServiceFqn, new String[] {ifcname, svcname});
			
		// --- <<IS-END>> ---

                
	}



	public static final void unregisterMetrics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(unregisterMetrics)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional ifcname
		// [i] field:0:optional svcname
		// [i] field:0:optional metricsServiceFqn
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	ifcname = IDataUtil.getString( pipelineCursor, "ifcname" );
		String	svcname = IDataUtil.getString( pipelineCursor, "svcname" );
		String	metricsServiceFqn = IDataUtil.getString( pipelineCursor, "metricsServiceFqn" );
		pipelineCursor.destroy();
		
		if( (ifcname == null || "".equals(ifcname) || svcname == null || "".equals(svcname) ) && (metricsServiceFqn == null || "".equals(metricsServiceFqn) )) {
			throw new ServiceException("Provide the folder and service name for the service implementing the wx.prometheus.pub.documents:prometheusMetric specification");
		}
		
		if( metricsServiceFqn == null || "".equals(metricsServiceFqn) ) {
			metricsServiceFqn = ifcname + ":" + svcname;
		}
		
		logger.debug("Removed service " + metricsServiceFqn + " from the list of registered metrics services.");
		if (metricsServiceFqn != null) registeredMetrics.remove(metricsServiceFqn);
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	//	private static java.util.List<String[]> registeredMetrics = new java.util.ArrayList<String[]>() ;
	private static java.util.Map<String, String[]> registeredMetrics = new java.util.HashMap<String, String[]>() ;
	static Logger logger = Logger.getLogger("wx.prometheus");
	// --- <<IS-END-SHARED>> ---
}

