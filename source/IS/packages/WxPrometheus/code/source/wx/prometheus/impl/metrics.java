package wx.prometheus.impl;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Map;
import com.softwareag.util.IDataMap;
// --- <<IS-END-IMPORTS>> ---

public final class metrics

{
	// ---( internal utility methods )---

	final static metrics _instance = new metrics();

	static metrics _newInstance() { return new metrics(); }

	static metrics _cast(Object o) { return (metrics)o; }

	// ---( server methods )---




	public static final void metricToText (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(metricToText)>> ---
		// @sigtype java 3.5
		// [i] recref:1:required metricsPrometheus wx.prometheus.pub.documents:text
		// [i] field:1:optional textMetricsString
		// [i] object:1:required metricsBytes
		// [o] field:0:required text
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		// metrics
		IData[]		metricsPrometheus = IDataUtil.getIDataArray( pipelineCursor, "metricsPrometheus" );
		String[]	textMetricsString = IDataUtil.getStringArray( pipelineCursor, "textMetricsString" );
		Object[]	metricsBytes = IDataUtil.getObjectArray( pipelineCursor, "metricsBytes" );
		
		pipelineCursor.destroy();
		
		if ( (metricsPrometheus == null) && (textMetricsString == null) && (metricsBytes == null)) {
			return;
		}
		StringBuilder text = new StringBuilder();
		if (metricsPrometheus != null) {
			for ( int i = 0; i < metricsPrometheus.length; i++ ) {
				IDataCursor metricsPrometheusCursor = metricsPrometheus[i].getCursor();
				String  HELP = IDataUtil.getString( metricsPrometheusCursor, "HELP" );
		        String  TYPE = IDataUtil.getString( metricsPrometheusCursor, "TYPE" );	
				IData[] metrics = IDataUtil.getIDataArray(metricsPrometheusCursor, "metrics");
				IData[] records = IDataUtil.getIDataArray(metricsPrometheusCursor, "records");
				metricsPrometheusCursor.destroy();
				if (records == null && metrics == null) {
					continue;
				}
				if ( metrics != null ) {	
					text.append(getLegacyMetrics(TYPE, HELP, metrics));
				}
				if ( records != null ) {	
					text.append(getMetrics(records));		
				}
			}
		}
		if (textMetricsString != null) {
			for ( int i_2 = 0; i_2 < textMetricsString.length; i_2++ ) {
				text.append(textMetricsString[i_2]);
			}
		}
		if (metricsBytes != null) {
			for ( int i_3 = 0; i_3 < metricsBytes.length; i_3++ ) {
				text.append(new String(((byte[])metricsBytes[i_3])));
			}
		}
		if( text.length() == 0 ) {
			text.append("# WxPrometheus got nothing for you right now...");
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "text", text.toString() );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static final String NEWLINE = "\n";
	
	private static final String getLegacyMetrics(String TYPE, String HELP, IData[] metrics) {
		StringBuilder text = new StringBuilder();
		for ( int i_1 = 0; i_1 < metrics.length; i_1++ )
	    {
	        IDataCursor metricsCursor = metrics[i_1].getCursor();
	            String  metric_name = IDataUtil.getString( metricsCursor, "metric_name" );
	            if( metric_name == null ) {
	                continue;
	            }
	            Object  value = IDataUtil.get( metricsCursor, "value" );
	            if( value == null ) {
	//                          value = new String("n/a");
	            	value = -1.0;
	//                          continue;
	            }
	            if( HELP != null && !"".equals(HELP)) 
	                text.append("# HELP " + metric_name + " " + HELP + NEWLINE);
	            
	            if( TYPE != null && !"".equals(TYPE)) 
	                text.append("# TYPE " + metric_name + " " + TYPE + NEWLINE);
	            text.append(metric_name);
	
	            // i_2.labels
	            IData[] labels = IDataUtil.getIDataArray( metricsCursor, "labels" );
	            if ( labels != null)
	            {
	                text.append("{");
	                for ( int i_2 = 0; i_2 < labels.length; i_2++ ) {
	                    IDataCursor labelsCursor = labels[i_2].getCursor();
	                    String  label_name = IDataUtil.getString( labelsCursor, "label_name" );
	                    String  label_value = IDataUtil.getString( labelsCursor, "label_value" );
	                    if( label_name == null ) {
	                        continue;
	                    }
	                    if( i_2 > 0 ) {
	                        text.append(","); 
	                    }
	                    if( label_value == null ) {
	                        label_value = "";
	                    }
	                    text.append(label_name + "=\"" + label_value + "\"");
	                    labelsCursor.destroy();
	                }
	                text.append("}");
	            }
	            text.append(" " + value);
	            Object  timestamp = IDataUtil.get( metricsCursor, "timestamp" );
	            if( timestamp != null && !"".equals(timestamp)) 
	                text.append(" " + timestamp);
	            metricsCursor.destroy();
	            text.append(NEWLINE);
	    }
		return text.toString();
	}
	
	private static final String getMetrics(IData[] records) {
		StringBuilder text = new StringBuilder();
		String HELP, TYPE;
		for ( int i_1 = 0; i_1 < records.length; i_1++) {
			IDataCursor metricsCursor = records[i_1].getCursor();
			HELP = IDataUtil.getString( metricsCursor, "help" );
			TYPE = IDataUtil.getString( metricsCursor, "type" );
		
			String	statName = IDataUtil.getString( metricsCursor, "statName" );
			if( statName == null ) {
				continue;
			}
			Object	value = IDataUtil.get( metricsCursor, "value" );
			String	timestamp = IDataUtil.getString( metricsCursor, "timestamp" );
			IData	labels = IDataUtil.getIData( metricsCursor, "labels" );
			metricsCursor.destroy();
			
			if( value == null ) {
	//				value = -1.0;
				continue;
			}
			if( HELP != null && !"".equals(HELP)) 
				text.append("# HELP " + statName + " " + HELP + NEWLINE);
					
			if( TYPE != null && !"".equals(TYPE)) 
				text.append("# TYPE " + statName + " " + TYPE + NEWLINE);
						
			text.append(statName);
		
			// index.labels
			
			IDataMap iMap = new IDataMap(labels);
			if ( iMap != null)
			{
				text.append("{");
				String	label_value;
				int index = 0;
				for ( String label_name : iMap.keySet()) {									
					label_value = iMap.getAsString(label_name);
					if( label_name == null ) {
						continue;
					}
					if( index > 0 ) {
						text.append(",");	
					}
					if( label_value == null ) {
						label_value = "";
					}
					text.append(label_name + "=\"" + label_value + "\"");
					index++;
				}
				text.append("}");
			}
			text.append(" " + value);			
			if( timestamp != null && !"".equals(timestamp)) 
				text.append(" " + timestamp);
			text.append(NEWLINE);			
		}							
		return text.toString();
	}
	// --- <<IS-END-SHARED>> ---
}

