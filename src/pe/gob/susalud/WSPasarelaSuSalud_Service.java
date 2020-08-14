
package pe.gob.susalud;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WSPasarelaSuSalud", targetNamespace = "http://www.susalud.gob.pe/acreditacion/WSPasarelaSuSalud/", wsdlLocation = "http://app26.susalud.gob.pe:27801/ServicePasarela?wsdl")
public class WSPasarelaSuSalud_Service
    extends Service
{

    private final static URL WSPASARELASUSALUD_WSDL_LOCATION;
    private final static WebServiceException WSPASARELASUSALUD_EXCEPTION;
    private final static QName WSPASARELASUSALUD_QNAME = new QName("http://www.susalud.gob.pe/acreditacion/WSPasarelaSuSalud/", "WSPasarelaSuSalud");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://app26.susalud.gob.pe:27801/ServicePasarela?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSPASARELASUSALUD_WSDL_LOCATION = url;
        WSPASARELASUSALUD_EXCEPTION = e;
    }

    public WSPasarelaSuSalud_Service() {
        super(__getWsdlLocation(), WSPASARELASUSALUD_QNAME);
    }

    public WSPasarelaSuSalud_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSPASARELASUSALUD_QNAME, features);
    }

    public WSPasarelaSuSalud_Service(URL wsdlLocation) {
        super(wsdlLocation, WSPASARELASUSALUD_QNAME);
    }

    public WSPasarelaSuSalud_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSPASARELASUSALUD_QNAME, features);
    }

    public WSPasarelaSuSalud_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WSPasarelaSuSalud_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WSPasarelaSuSalud
     */
    @WebEndpoint(name = "WSPasarelaSuSaludSOAP")
    public WSPasarelaSuSalud getWSPasarelaSuSaludSOAP() {
        return super.getPort(new QName("http://www.susalud.gob.pe/acreditacion/WSPasarelaSuSalud/", "WSPasarelaSuSaludSOAP"), WSPasarelaSuSalud.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSPasarelaSuSalud
     */
    @WebEndpoint(name = "WSPasarelaSuSaludSOAP")
    public WSPasarelaSuSalud getWSPasarelaSuSaludSOAP(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.susalud.gob.pe/acreditacion/WSPasarelaSuSalud/", "WSPasarelaSuSaludSOAP"), WSPasarelaSuSalud.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSPASARELASUSALUD_EXCEPTION!= null) {
            throw WSPASARELASUSALUD_EXCEPTION;
        }
        return WSPASARELASUSALUD_WSDL_LOCATION;
    }

}