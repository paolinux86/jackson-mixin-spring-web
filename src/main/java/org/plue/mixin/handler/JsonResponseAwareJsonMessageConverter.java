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

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.plue.mixin.annotation.JsonMixin;
import org.plue.mixin.response.ResponseWrapper;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author p.cortis@sinossi.it
 */
public class JsonResponseAwareJsonMessageConverter extends MappingJackson2HttpMessageConverter
{
	public JsonResponseAwareJsonMessageConverter()
	{
		ObjectMapper defaultObjectMapper = new ObjectMapper();
		defaultObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		setObjectMapper(defaultObjectMapper);
	}

	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException
	{
		if(object instanceof ResponseWrapper) {
			writeJson((ResponseWrapper) object, outputMessage);
			return;
		}

		super.writeInternal(object, type, outputMessage);
	}

	protected void writeJson(ResponseWrapper response, HttpOutputMessage outputMessage) throws IOException
	{
		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		JsonMixin[] mixins = response.getJsonResponse().mixins();
		Arrays.stream(mixins)
				.forEach(m -> mapper.addMixIn(m.target(), m.mixin()));

		JsonGenerator jsonGenerator = mapper.getFactory().createGenerator(outputMessage.getBody(), encoding);
		mapper.writeValue(jsonGenerator, response.getOriginalResponse());
	}
}
