/**
 *    Copyright 2009-2019 the original author or authors.
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
package org.zk.simplemybatis.transaction;


import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * 创建{@link Transaction}实例
 */
public interface TransactionFactory {

  default void setProperties(Properties props) {
    // NOP
  }


  Transaction newTransaction(DataSource dataSource);

}
