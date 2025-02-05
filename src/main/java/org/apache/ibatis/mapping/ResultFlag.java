/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.mapping;

/**
 * resultMap子标签的其中两种类型
 * ID：对应<id/>子标签，是数据库返回数据的主键
 * CONSTRUCTOR：对应<constructor/>子标签，表明返回的某些字段可作为对象的构造参数
 * @author Clinton Begin
 */
public enum ResultFlag {
  ID, CONSTRUCTOR
}
