// This file is part of jackson-mixin-spring-web.
//
// jackson-mixin-spring-web is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// jackson-mixin-spring-web is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with jackson-mixin-spring-web.  If not, see <http://www.gnu.org/licenses/>.
package org.plue.mixin.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author p.cortis@sinossi.it
 */
public class JsonResponseSupportFactoryBean implements InitializingBean
{
	@Autowired
	private RequestMappingHandlerAdapter adapter;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList(this.adapter.getReturnValueHandlers());

		decorateHandlers(handlers);
		this.adapter.setReturnValueHandlers(handlers);
	}

	private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers)
	{
		handlers.parallelStream()
				.filter(h -> h instanceof RequestResponseBodyMethodProcessor)
				.forEach(h -> {
					JsonResponseInjectingReturnValueHandler decorator = new JsonResponseInjectingReturnValueHandler(
							h);
					int index = handlers.indexOf(h);
					handlers.set(index, decorator);
				});
	}
}
