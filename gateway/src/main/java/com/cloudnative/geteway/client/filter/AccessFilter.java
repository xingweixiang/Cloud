package com.cloudnative.geteway.client.filter;

import com.cloudnative.base.support.constant.UaaConstant;
import com.cloudnative.base.support.util.TokenUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AccessFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		try {
			RequestContext ctx = RequestContext.getCurrentContext();
			HttpServletRequest request = ctx.getRequest();
			// 一定要get一下,下面这行代码才能取到值... [注1]
			request.getParameterMap();

			Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
			Map<String, String> requestHeaders = ctx.getZuulRequestHeaders();
			
			
			if (requestQueryParams == null) {
				requestQueryParams = new HashMap<>();
			}
			ctx.setRequestQueryParams(requestQueryParams);
			
			if (StringUtils.isNotBlank(TokenUtil.getToken()))   {

				ctx.addZuulRequestHeader(UaaConstant.TOKEN_HEADER, TokenUtil.getToken());
			}

		} catch (Exception e) {

		}
		return null;
	}

}
