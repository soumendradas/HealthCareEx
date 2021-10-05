package in.nareshit.raghu.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import in.nareshit.raghu.entity.Specialization;

//JDK 8 (static methods + default methods allowed)
public interface MyCollectionsUtil {

	public static Map<Long, String> convertToMap(List<Object[]> list) {
		
		//Java 8 Stream API
		return list.stream().collect(Collectors.toMap(
				ob->Long.valueOf(ob[0].toString()), 
				ob->ob[1].toString()));
	}
	
	public static Map<Long, String> convertToMapIndex(List<Object[]> list) {
		
		Map<Long, String> map = list.stream()
							.collect(Collectors.toMap(d->Long.parseLong(d[0].toString()),
									d->d[1].toString()+" "+d[2].toString()+
									" ("+((Specialization) d[3]).getSpecName()+")"));
		
		return map;
	}
}
