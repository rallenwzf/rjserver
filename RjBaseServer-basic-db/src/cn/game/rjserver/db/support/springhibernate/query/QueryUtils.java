package cn.game.rjserver.db.support.springhibernate.query;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.Type;
import org.hibernate.type.IntegerType;
import org.springframework.beans.BeanUtils;

public class QueryUtils {
	public static String POJO_PKID = "id";

	public QueryUtils() {
	}

	public static Method getMethod(Class ref, String propertyName) {
		if (propertyName.indexOf(".") > 0)
			propertyName = propertyName.substring(0, propertyName.indexOf("."));
		String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		Method method = BeanUtils.findMethod(ref, methodName, null);
		return method;
	}

	public static ShQuery getQuery(Map<String, String> requestParameters, Class refClass) throws ParseException {
		ShQuery query = new ShQuery(refClass);
		for (Iterator _enum = requestParameters.keySet().iterator(); _enum.hasNext();) {
			String paraName = (String) _enum.next();
			Method method = getMethod(refClass, paraName);
			if (method != null) {
				String paraValue = requestParameters.get(paraName);
				if (paraValue != null && paraValue.length() > 0) {
					query.addParaName(paraName);
					query.addParaValue(getRequestValue(paraName, method, paraValue));
					query.addParaType(getHibernateType(method.getReturnType()));
					// System.out.println(paraName +
					// "=paraName-------paraValue=" + paraValue
					// + "------method.getReturnType()=" +
					// method.getReturnType());
					String paraOp = requestParameters.get(paraName + "_op");
					if (paraOp == null || paraOp.length() <= 0)
						paraOp = "=";
					query.addParaOprator(paraOp);
				}
			} else {
				try {
					if (paraName != null && paraName.indexOf("orderBy") == 0) {
						String paraValue = requestParameters.get(paraName);
						String[] s = paraValue.split(",");
						for (int i = 0; i < s.length; i++) {
							if (getMethod(refClass, s[i].split(" ")[0]) != null) {
								query.addOrderBy(s[i]);
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}

		return query;
	}

	public static Object getRequestValue(String paraName, Method method, String paraValue) throws ParseException {
		Class returnType = method.getReturnType();
		if (returnType.equals(java.util.Date.class)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(paraValue);
		} else if (returnType.equals(int.class)) {
			return Integer.parseInt(paraValue);
		} else if (returnType.equals(boolean.class)) {
			return Boolean.parseBoolean(paraValue);
		} else {
			if (paraValue.matches("\\d+") && paraName.contains(POJO_PKID)) {
				return Integer.parseInt(paraValue);
			}
			return paraValue;
		}
	}

	public static Type getHibernateType(Class propertyType) {
		if (propertyType.equals(java.util.Date.class))
			return new DateType();
		else if (propertyType.equals(int.class))
			return new IntegerType();
		else if (propertyType.equals(boolean.class))
			return new BooleanType();
		else
			return null;
	}

	public static String getHQL(ShQuery query) {
		String hql = "";
		if (query.getRefClass() != null) {
			hql = "From " + query.getRefClass().getName() + " As theObject Where 1=1 ";
			for (int i = 0; i < query.getParaNames().size(); i++)
				if (query.getParaOprators().size() == 0)
					hql = hql + " And theObject." + (String) query.getParaNames().get(i) + "=?";
				else
					hql = hql + " And theObject." + (String) query.getParaNames().get(i)
							+ (String) query.getParaOprators().get(i) + "?";

			for (int i = 0; i < query.getWheres().size(); i++) {
				hql = hql + " And theObject." + (String) query.getWheres().get(i);
			}

			if (query.isDefaultOrderBy() && query.getOrderBys().size() < 1) {
				hql = hql + " Order By theObject." + POJO_PKID + " desc";
			} else {
				for (int i = 0; i < query.getOrderBys().size(); i++)
					if (i == 0)
						hql = hql + " Order By theObject." + query.getOrderBys().get(i);
					else
						hql = hql + ", theObject." + query.getOrderBys().get(i);

			}

		}
		return hql;
	}
}
