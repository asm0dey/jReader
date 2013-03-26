package com.github.asm0dey.server.services;

import com.github.asm0dey.shared.opml.OpmlBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.simpleframework.xml.core.Persister;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: finkel
 * <p/>
 * Date: 26.03.13
 * <p/>
 * Time: 11:13
 */
@Service( "myhttprequesthandler" )
public class OpmlUploadServlet extends HttpServlet {
	private final Logger logger = Logger.getLogger( "UploadServlet" );

	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		try {
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload( fileItemFactory );
			long FILE_SIZE_LIMIT = 20 * 1024 * 1024;
			fileUpload.setSizeMax( FILE_SIZE_LIMIT );

			List<FileItem> items = fileUpload.parseRequest( req );

			for ( FileItem item : items ) {

				if ( !item.isFormField() ) {
					if ( item.getSize() > FILE_SIZE_LIMIT ) {
						resp.sendError( HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "File size exceeds limit" );

						return;
					}

					OpmlBean opmlBean = new Persister().read( OpmlBean.class, item.getString() );
					new XStream( new JettisonMappedXmlDriver() ).toXML( opmlBean, resp.getWriter() );
					if ( !item.isInMemory() )
						item.delete();
				}
			}
		} catch ( Exception e ) {
			logger.log( Level.ERROR, "Throwing servlet exception for unhandled exception", e );
			resp.sendError( HttpServletResponse.SC_BAD_REQUEST );
			throw new ServletException( e );
		}
	}

}
