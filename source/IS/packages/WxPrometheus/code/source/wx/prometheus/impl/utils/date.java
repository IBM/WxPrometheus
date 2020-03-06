package wx.prometheus.impl.utils;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class date

{
	// ---( internal utility methods )---

	final static date _instance = new date();

	static date _newInstance() { return new date(); }

	static date _cast(Object o) { return (date)o; }

	// ---( server methods )---




	public static final void currentTimeMillis (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(currentTimeMillis)>> ---
		// @sigtype java 3.5
		// [o] object:0:required currentTimeMillis
		java.util.Date now = new java.util.Date();
		IDataCursor idc_pipeline = pipeline.getCursor();
		IDataUtil.put(idc_pipeline, "currentTimeMillis", now.getTime());
		IDataUtil.put(idc_pipeline, "currentTimeMillisString", Long.toString(now.getTime()));
		idc_pipeline.destroy();
		// --- <<IS-END>> ---

                
	}
}

