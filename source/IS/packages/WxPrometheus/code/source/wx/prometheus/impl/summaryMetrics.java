package wx.prometheus.impl;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import com.softwareag.is.log.Log;
import com.softwareag.util.IDataMap;
import io.prometheus.client.Gauge;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.common.TextFormat;
import org.apache.log4j.Logger;
// --- <<IS-END-IMPORTS>> ---

public final class summaryMetrics

{
	// ---( internal utility methods )---

	final static summaryMetrics _instance = new summaryMetrics();

	static summaryMetrics _newInstance() { return new summaryMetrics(); }

	static summaryMetrics _cast(Object o) { return (summaryMetrics)o; }

	// ---( server methods )---




	public static final void getServiceExecutionTimeMetrics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getServiceExecutionTimeMetrics)>> ---
		// @sigtype java 3.5
		// [o] field:1:optional textMetricsString
		IDataMap plMap = new IDataMap(pipeline);
		StringWriter sw = new StringWriter();
		try {
			TextFormat.write004(sw, Collections.enumeration(serviceExecutionTime.collect()));
			TextFormat.write004(sw, Collections.enumeration(avgServiceExecutionTime.collect()));
		} catch (IOException e) {
			logger.error("Failed to render service execution time metrics: "+e.getMessage());
		}
		plMap.put("textMetricsString", new String[] {sw.toString()});
			
		// --- <<IS-END>> ---

                
	}



	public static final void observeServiceExecutionTime (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(observeServiceExecutionTime)>> ---
		// @sigtype java 3.5
		// [i] field:0:required elapsedTimeMilliSeconds
		// [i] field:0:required serviceName
		// [o] field:0:required avgTimeMilliSeconds
		IDataMap plMap = new IDataMap(pipeline);
		String elapsedTimeMilliSeconds = plMap.getAsString("elapsedTimeMilliSeconds");
		String serviceName = plMap.getAsString("serviceName");
		serviceExecutionTime.labels(serviceName).observe(Double.parseDouble(elapsedTimeMilliSeconds));
		avgServiceExecutionTime.labels(serviceName).set(serviceExecutionTime.labels(serviceName).get().sum / serviceExecutionTime.labels(serviceName).get().count);
		plMap.put("avgTimeMilliSeconds", new DecimalFormat("#.#####").format(avgServiceExecutionTime.labels(serviceName).get()));
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static final Summary serviceExecutionTime = Summary.build()
			.name("serviceExecutionTime")
			.labelNames("serviceName")
			.quantile(0.05, 0.001)
			.quantile(0.5, 0.001)
			.quantile(0.95, 0.001)
			.help("Service executions time in Milliseconds")	
			.create();
	
	public static final Gauge avgServiceExecutionTime = Gauge.build()
			.name("avgServiceExecutionTime")
			.labelNames("serviceName")
			.help("Average service execution time in Milliseconds")
			.create();
	
	static Logger logger = Logger.getLogger("wx.prometheus");
		
	// --- <<IS-END-SHARED>> ---
}

