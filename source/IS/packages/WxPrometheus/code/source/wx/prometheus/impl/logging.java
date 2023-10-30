package wx.prometheus.impl;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.util.JournalLogger;
import java.io.File;
import com.wm.app.b2b.server.ServerAPI;
import com.wm.data.IData;
import com.wm.app.log.*;
// --- <<IS-END-IMPORTS>> ---

public final class logging

{
	// ---( internal utility methods )---

	final static logging _instance = new logging();

	static logging _newInstance() { return new logging(); }

	static logging _cast(Object o) { return (logging)o; }

	// ---( server methods )---




	public static final void logMessage (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(logMessage)>> ---
		// @sigtype java 3.5
		// [i] field:0:required logMessage
		// [i] field:0:required severity {"TRACE","DEBUG","INFO","WARN","ERROR","FATAL"}
		// [i] field:0:optional logger
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	logMessage = IDataUtil.getString( pipelineCursor, "logMessage" );
		String	severity = IDataUtil.getString( pipelineCursor, "severity" );
		String logger = IDataUtil.getString( pipelineCursor, "logger" );
		String prefix = IDataUtil.getString( pipelineCursor, "prefix" );
		pipelineCursor.destroy();
		
		if ( logger == null ) {
			prefix = "wx.prometheus";
		}
		if ( prefix != null ) {
			prefix = logger + "." + prefix;
		}
		else {
			prefix = logger;
		}
		
		if( severity.equalsIgnoreCase("warn") ) {
			JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.WARNING, prefix, logMessage );
		} else if( severity.equalsIgnoreCase("error") ) {
			JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.ERROR, prefix, logMessage );
		} else if( severity.equalsIgnoreCase("fatal") ) {
			JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.CRITICAL, prefix, logMessage );
		} else if( severity.equalsIgnoreCase("info") ) {
			JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.INFO, prefix, logMessage );
		} else if( severity.equalsIgnoreCase("debug") ) {
			JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.DEBUG, prefix, logMessage );
		} else {
			JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.TRACE, prefix, logMessage );
		}
			
		// --- <<IS-END>> ---

                
	}
}

