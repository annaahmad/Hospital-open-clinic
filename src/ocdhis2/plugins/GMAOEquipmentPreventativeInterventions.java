package ocdhis2.plugins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

import org.dom4j.Element;

import be.mxs.common.util.db.MedwanQuery;
import be.mxs.common.util.system.Debug;
import be.openclinic.system.SH;
import net.admin.Service;
import ocdhis2.DHIS2Plugin;

public class GMAOEquipmentPreventativeInterventions extends DHIS2Plugin {

	@Override
	public Vector getItems(Date begin, Date end, Element dataset, Hashtable pluginParameters) {
		return null;
	}
	
	@Override
	public Vector getResults(Date begin, Date end, Element dataset, Hashtable pluginParameters) {
		Hashtable costcenters = Service.getCostCenters();
		Hashtable<String,Integer> operations = be.openclinic.assets.Util.getPreventativeInterventions("e",begin,end);
		Hashtable<String,Integer> plannedoperations = be.openclinic.assets.Util.getPlannedPreventativeInterventions("e",begin,end);
		Hashtable<String,Integer> expiredoperations = be.openclinic.assets.Util.getExpiredPlannedPreventativeInterventions("e",begin,end);
		Vector items = new Vector();
		try {
			Vector<String> scores = new Vector<String>();
			String rootService=SH.c((String)pluginParameters.get("organisationlevel"));
			if(rootService.length()>0) {
				if(SH.hasParentKey(costcenters,rootService) && (SH.checkInteger(plannedoperations.get(rootService),0)+SH.checkInteger(expiredoperations.get(rootService),0))>0) {
					scores.add(SH.getParentValue(costcenters,rootService)+";"+SH.checkInteger(operations.get(rootService),0)+";"+SH.checkInteger(plannedoperations.get(rootService),0)+";"+(SH.checkInteger(plannedoperations.get(rootService),0)+SH.checkInteger(expiredoperations.get(rootService),0)));
				}
				Vector children=Service.getChildIds(rootService);
				for(int n=0;n<children.size();n++){
					String service=(String)children.elementAt(n);
					if(SH.hasParentKey(costcenters,service) && (SH.checkInteger(plannedoperations.get(service),0)+SH.checkInteger(expiredoperations.get(service),0))>0) {
						scores.add(SH.getParentValue(costcenters,service)+";"+SH.checkInteger(operations.get(service),0)+";"+SH.checkInteger(plannedoperations.get(service),0)+";"+(SH.checkInteger(plannedoperations.get(service),0)+SH.checkInteger(expiredoperations.get(service),0)));
					}
				}
			}
			String[] categories = "cds;hd;h3;hc".split(";");
			int[][] results = new int[categories.length][3];
			for(int n=0;n<scores.size();n++) {
				String score = scores.elementAt(n);
				for(int i=0;i<categories.length;i++) {
					if(score.startsWith(categories[i]+";")) {
						int done =Integer.parseInt(score.split(";")[1]);
						int planned =Integer.parseInt(score.split(";")[2]);
						int plannedexpired =Integer.parseInt(score.split(";")[3]);
						if(done>plannedexpired) {
							done=plannedexpired;
						}
						results[i][0]+=done;
						results[i][1]+=planned;
						results[i][2]+=plannedexpired;
						break;
					}
				}
			}
			for(int i=0;i<categories.length;i++) {
				for(int n=0;n<3;n++) {
					if(results[i][n]>0) items.add(results[i][n]+";"+(n+1)+";"+categories[i]);
				} 
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return items;
	}

}
