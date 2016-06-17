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
package org.plue.mixin.response.impl;

import org.plue.mixin.annotation.JsonResponse;
import org.plue.mixin.response.ResponseWrapper;

/**
 * @author Paolo Cortis
 */
public final class ResponseWrapperImpl implements ResponseWrapper
{
	private final Object originalResponse;

	private final JsonResponse jsonResponse;

	public ResponseWrapperImpl(Object originalResponse, JsonResponse jsonResponse)
	{
		super();
		this.originalResponse = originalResponse;
		this.jsonResponse = jsonResponse;
	}

	@Override
	public boolean hasJsonMixins()
	{
		return this.jsonResponse.mixins().length > 0;
	}

	@Override
	public JsonResponse getJsonResponse()
	{
		return this.jsonResponse;
	}

	@Override
	public Object getOriginalResponse()
	{
		return this.originalResponse;
	}
}
