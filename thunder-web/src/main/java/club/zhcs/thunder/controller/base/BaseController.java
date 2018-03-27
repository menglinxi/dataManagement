package club.zhcs.thunder.controller.base;

import club.zhcs.thunder.bean.acl.User;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.View;
import org.nutz.mvc.view.ForwardView;
import org.nutz.mvc.view.JspView;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.mvc.view.UTF8JsonView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author kerbores@gmail.com
 *
 */
public class BaseController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	protected Log log = Logs.get();

	public Logger logger = Logger.getLogger(getClass());

	protected void _addCookie(String name, String value, int age) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(age);
		response.addCookie(cookie);
	}

	public HttpServletRequest request() {
		return request;
	}

	public String _base() {
		return request.getContextPath();
	}

	public UserAgent _ua() {
		logger.debug(request.getHeader("user-agent"));
		return new UserAgent(request.getHeader("user-agent"));
	}

	public int _fixPage(int page) {
		return ((page <= 0) ? 1 : page);
	}

	public String _fixSearchKey(String key) {
		if ((Strings.equalsIgnoreCase("get", request.getMethod())) && (Lang.isWin())) {
			key = (Strings.isBlank(key)) ? "" : key;
			try {
				return new String(key.getBytes("UTF-8"), request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				logger.debug(e);
				return "";
			}
		}
		return ((Strings.isBlank(key)) ? "" : key);
	}

	protected String _getCookie(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Strings.equals(cookie.getName(), name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public String _getNameSpace() {
		return null;
	}

	public String _ip() {
		return Lang.getIP(request);
	}

	protected void _putSession(String key, Object value) {
		request.getSession().setAttribute(key, value);
	}

	public View _renderForward(String path, Object[] objs) {
		request.setAttribute("objs", objs);
		return new ForwardView(path);
	}

	public View _renderJson(Object[] objs) {
		UTF8JsonView view = (UTF8JsonView) UTF8JsonView.NICE;
		view.setData(objs);
		return view;
	}

	public View _renderJsp(String path, Object[] objs) {
		request.setAttribute("objs", objs);
		return new JspView(path);
	}

	public View _renderRedirct(String path) {
		return new ServerRedirectView(path);
	}



	public static long getUid() {
		long uid = -1;
		Object u;
		try {
			u = SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		} catch (Throwable e) {
			return -1;
		}
		if (u != null) {
			if (u instanceof User) {
				uid = ((User) u).getId();
			} else if (u instanceof Number) {
				uid = ((Number) u).intValue();
			}
		}
		return uid;
	}


	public static User getUser() {
		User user = null;
		try {
			user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
			return user;
		} catch (Throwable e) {
			return null;
		}
	}

}
