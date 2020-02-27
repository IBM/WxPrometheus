package wx.prometheus.impl;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
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
		// [o] field:0:required text
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		// metrics
		IData[]	metricsPrometheus = IDataUtil.getIDataArray( pipelineCursor, "metricsPrometheus" );
		if ( metricsPrometheus == null) {
			return;
		}
		StringBuffer text = new StringBuffer();
		for ( int i = 0; i < metricsPrometheus.length; i++ ){
			IDataCursor metricsPrometheusCursor = metricsPrometheus[i].getCursor();
				String	HELP = IDataUtil.getString( metricsPrometheusCursor, "HELP" );
				String	TYPE = IDataUtil.getString( metricsPrometheusCursor, "TYPE" );
		
				IData[]	metrics = IDataUtil.getIDataArray( metricsPrometheusCursor, "metrics" );
				if ( metrics == null) {
					continue;
				}
				for ( int i_1 = 0; i_1 < metrics.length; i_1++ )
				{
					IDataCursor metricsCursor = metrics[i_1].getCursor();
						String	metric_name = IDataUtil.getString( metricsCursor, "metric_name" );
						if( metric_name == null ) {
							continue;
						}
						Object	value = IDataUtil.get( metricsCursor, "value" );
						if( value == null ) {
		//											value = new String("n/a");
											value = -1.0;
		//							continue;
						}
						if( HELP != null && !"".equals(HELP)) 
							text.append("# HELP " + metric_name + " " + HELP + NEWLINE);
						
						if( TYPE != null && !"".equals(TYPE)) 
							text.append("# TYPE " + metric_name + " " + TYPE + NEWLINE);
						text.append(metric_name);
		
						// i_2.labels
						IData[]	labels = IDataUtil.getIDataArray( metricsCursor, "labels" );
						if ( labels != null)
						{
							text.append("{");
							for ( int i_2 = 0; i_2 < labels.length; i_2++ ) {
								IDataCursor labelsCursor = labels[i_2].getCursor();
								String	label_name = IDataUtil.getString( labelsCursor, "label_name" );
								String	label_value = IDataUtil.getString( labelsCursor, "label_value" );
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
						Object	timestamp = IDataUtil.get( metricsCursor, "timestamp" );
						if( timestamp != null && !"".equals(timestamp)) 
							text.append(" " + timestamp);
						metricsCursor.destroy();
						text.append(NEWLINE);
					
				}
				metricsPrometheusCursor.destroy();
		}
		pipelineCursor.destroy();
		
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
	// --- <<IS-END-SHARED>> ---
}

