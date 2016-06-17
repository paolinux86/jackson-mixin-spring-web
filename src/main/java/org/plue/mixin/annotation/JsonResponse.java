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
package org.plue.mixin.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Paolo Cortis
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonResponse
{
	public JsonMixin[] mixins();
}
