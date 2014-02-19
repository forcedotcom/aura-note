/*
 * Copyright (C) 2014 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
({
    getRowTitle : function(cmp, index) {
        return cmp.find("list").find("row")[index].getSuper().get("v.body")[0].getElement().textContent;
    },

    tearDown : function(cmp) {
        var a = cmp.get("c.deleteNotesByKey");
        a.setParams({ key : cmp.get("m.key") });
        $A.test.callServerAction(a);
    },

    testSortDateAsc:{
        attributes : { sort : "createdOn.asc" },
        test : function(cmp){
            $A.test.assertEquals("created first", this.getRowTitle(cmp, 0));
            $A.test.assertEquals("created second", this.getRowTitle(cmp, 1));
            $A.test.assertEquals("created third", this.getRowTitle(cmp, 2));
            $A.test.assertEquals("created absolutely last", this.getRowTitle(cmp, 3));
        }
    },

    testSortDateDesc:{
        attributes : { sort : "createdOn.desc" },
        test : function(cmp){
            $A.test.assertEquals("created absolutely last", this.getRowTitle(cmp, 0));
            $A.test.assertEquals("created third", this.getRowTitle(cmp, 1));
            $A.test.assertEquals("created second", this.getRowTitle(cmp, 2));
            $A.test.assertEquals("created first", this.getRowTitle(cmp, 3));
        }
    },

    testSortTitleAsc:{
        attributes : { sort : "title.asc" },
        test : function(cmp){
            $A.test.assertEquals("created absolutely last", this.getRowTitle(cmp, 0));
            $A.test.assertEquals("created first", this.getRowTitle(cmp, 1));
            $A.test.assertEquals("created second", this.getRowTitle(cmp, 2));
            $A.test.assertEquals("created third", this.getRowTitle(cmp, 3));
        }
    },

    testSortTitleDesc:{
        attributes : { sort : "title.desc" },
        test : function(cmp){
            $A.test.assertEquals("created third", this.getRowTitle(cmp, 0));
            $A.test.assertEquals("created second", this.getRowTitle(cmp, 1));
            $A.test.assertEquals("created first", this.getRowTitle(cmp, 2));
            $A.test.assertEquals("created absolutely last", this.getRowTitle(cmp, 3));
        }
    }
})
