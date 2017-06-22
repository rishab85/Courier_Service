package courierUI;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import courierDM.BestRoute;
import courierDM.Map;
import courierDM.senderReceiver;

public class generateRoute {

	private BestRoute best = new BestRoute();
	private String route;
	public generateRoute() {
		// TODO Auto-generated constructor stub
		
	}

	
	public BestRoute generate(senderReceiver sR, String from){
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session ss = sf.openSession();
		Transaction tx = ss.beginTransaction();
		ArrayList<Map> map = new ArrayList<Map>();
		map.clear();
		map = (ArrayList<Map>) ss.createQuery("From Map")
				.list();
		ss.flush();
		tx.commit();
		ss.close();
		char c = 'H';
		String[][] mapL = new String[8][c];
		int index = 0;
		for(int i=1; i<=7 ; i++){
			for (char j = 'A'; j<'H'; j++) {
				mapL[i][j] = map.get(index).getDirection();
				index ++;
			}
		}
		
		
		int s = sR.getSourceStreet();
		int d = sR.getDestStreet();
		char ns=sR.getSourceAve(), nd = sR.getDestAve(), n='H';
		int p = 0, as = 0, np = 0, rev2 = 0, rev1 = 0, miles=0;
		
		String[] seq = new String[4];
		
		//detection source node and destination node
		if(s>d){
			p = -1;
			as = s+1;
			seq[0] = "U";
			seq[3] = "D";
		}else{
			p = 1;
			as = d+1; 
			seq[0] = "D";
			seq[3] = "U";
		}
		
		if(ns>nd){
			n = ns;
			n+=1;
			np = -1;
			rev1 = 1;
			seq[1] = "L";
			seq[2] = "R";
		}else{
			n=nd;
			n+=1;
			np = 1;
			rev2 = -2;
			rev1 = -1;
				seq[1] = "R";
			seq[2] = "L";
		}
		String[][] a = new String[as][n];
		CharSequence cs[] = (CharSequence[]) seq;
		CharSequence block = "B";
		System.out.println(mapL[s][ns]);
		ArrayList<String> visited = new ArrayList<String>();
		for(int i=s; s>d?i>=d:i<=d ; i+=p){
			for (char cc = ns; ns > nd?cc>=nd:cc<=nd; cc+=np) {
				if(d==i){
					if(cc==nd){
						System.out.println(i + " " + cc);
						route = route + "S" +  String.valueOf(i) + String.valueOf(cc);
						miles+=1;
						break;
					}else{
						route = route + String.valueOf(cs[1])  +  String.valueOf(i) + String.valueOf(cc);
						System.out.println(i + " " + cc);
					}
				}else if(d == i+p){
					if(mapL[i+p][cc].contains(cs[1]) && !mapL[i][cc].contains(block)){
						System.out.println(i + " " + cc);
						route = route + String.valueOf(cs[0]) +  String.valueOf(i) + String.valueOf(cc);
						miles+=1;
						break;
					}else{
						
						if(cc == nd){
							ns = cc;
							miles+=1;
							System.out.println(i + " " + cc);
							route = route + String.valueOf(cs[0]) +  String.valueOf(i) + String.valueOf(cc);
							break;
						}else{
							miles+=1;
							System.out.println(i + " " + cc);
							route = route + String.valueOf(cs[1]) +  String.valueOf(i) + String.valueOf(cc);
						}
					}
				}else{
					if(mapL[i][cc].contains(cs[0]) && !mapL[i][cc].contains(block)){
						ns = cc;
						miles+=1;
						System.out.println(i + " " + cc);
						route = route + String.valueOf(cs[0]) +  String.valueOf(i) + String.valueOf(cc);
						break;
					}else if(mapL[i][cc].contains(cs[1])&& !mapL[i][cc].contains(block)){
						miles+=1;
						System.out.println(i + " " + cc);
						route = route + String.valueOf(cs[1]) +  String.valueOf(i) + String.valueOf(cc);
					}else if(mapL[i][cc].contains(cs[2]) && !mapL[i][cc].contains(block)){
						miles+=1;
						System.out.println(i + " " + cc);
						route = route + String.valueOf(cs[2]) +  String.valueOf(i) + String.valueOf(cc);
						cc+=rev2;
						ns+=rev1;
					}else if (mapL[i][cc].contains(cs[3]) && !mapL[i][cc].contains(block)){
						i+=2;
						miles+=1;
						route = route + String.valueOf(cs[3]) +  String.valueOf(i) + String.valueOf(cc);
						break;
					}else{
						System.out.println("Sorry Destination cant be reached, Because all routes are blocked");
						return best;
					}
				}		
			}
		}
		route = route.replaceAll("null", "");
		best.setRoute(route);
		best.setTotalMiles(miles);
		best.setRouteFrom(from);
		return best;
		
	}
}
