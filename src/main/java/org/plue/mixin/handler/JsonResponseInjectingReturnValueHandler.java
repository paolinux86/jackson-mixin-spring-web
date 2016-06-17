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

import org.plue.mixin.annotation.JsonResponse;
import org.plue.mixin.response.impl.ResponseWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author p.cortis@sinossi.it
 */
public final class JsonResponseInjectingReturnValueHandler implements HandlerMethodReturnValueHandler
{
	private final HandlerMethodReturnValueHandler delegate;

	public JsonResponseInjectingReturnValueHandler(HandlerMethodReturnValueHandler delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType)
	{
		return this.delegate.supportsReturnType(returnType);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception
	{
		JsonResponse jsonResponse = returnType.getMethodAnnotation(JsonResponse.class);
		if(jsonResponse != null) {
			returnValue = new ResponseWrapperImpl(returnValue, jsonResponse);
		}

		this.delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
	}
}
