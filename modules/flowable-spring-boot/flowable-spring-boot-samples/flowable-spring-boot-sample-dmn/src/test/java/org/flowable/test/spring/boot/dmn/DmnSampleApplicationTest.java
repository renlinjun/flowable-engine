/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.test.spring.boot.dmn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.HashMap;
import java.util.Map;

import org.flowable.dmn.api.DmnRepositoryService;
import org.flowable.dmn.api.DmnRuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import flowable.DmnSampleApplication;

/**
 * @author Filip Hrisafov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DmnSampleApplication.class)
public class DmnSampleApplicationTest {

    @Autowired
    private DmnRepositoryService repositoryService;

    @Autowired
    private DmnRuleService ruleService;

    @Test
    public void contextLoads() {
        //TODO Once #832 is resolved use the default folder location

        repositoryService.createDeployment().addClasspathResource("simple.dmn.xml").deploy();

        Map<String, Object> variables = new HashMap<>();
        variables.put("inputVariable1", 1);
        variables.put("inputVariable2", "test1");
        Map<String, Object> result = ruleService.createExecuteDecisionBuilder().decisionKey("simple").variables(variables).executeWithSingleResult();

        assertThat(result).contains(entry("outputVariable1", "result1"));

    }
}
