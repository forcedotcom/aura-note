({
    getRowTitle : function(cmp, index) {
        return cmp.find("list").find("row")[index].getSuper().get("v.body")[0].getElement().textContent;
    },

    testSortDateAsc:{
        attributes : { sort : "createdOn.asc" },
        test : function(cmp){
            $F.test.assertEquals("created first", this.getRowTitle(cmp, 0));
            $F.test.assertEquals("created second", this.getRowTitle(cmp, 1));
            $F.test.assertEquals("created third", this.getRowTitle(cmp, 2));
            $F.test.assertEquals("created absolutely last", this.getRowTitle(cmp, 3));
        }
    },

    testSortDateDesc:{
        attributes : { sort : "createdOn.desc" },
        test : function(cmp){
            $F.test.assertEquals("created absolutely last", this.getRowTitle(cmp, 0));
            $F.test.assertEquals("created third", this.getRowTitle(cmp, 1));
            $F.test.assertEquals("created second", this.getRowTitle(cmp, 2));
            $F.test.assertEquals("created first", this.getRowTitle(cmp, 3));
        }
    },

    testSortTitleAsc:{
        attributes : { sort : "title.asc" },
        test : function(cmp){
            $F.test.assertEquals("created absolutely last", this.getRowTitle(cmp, 0));
            $F.test.assertEquals("created first", this.getRowTitle(cmp, 1));
            $F.test.assertEquals("created second", this.getRowTitle(cmp, 2));
            $F.test.assertEquals("created third", this.getRowTitle(cmp, 3));
        }
    },

    testSortTitleDesc:{
        attributes : { sort : "title.desc" },
        test : function(cmp){
            $F.test.assertEquals("created third", this.getRowTitle(cmp, 0));
            $F.test.assertEquals("created second", this.getRowTitle(cmp, 1));
            $F.test.assertEquals("created first", this.getRowTitle(cmp, 2));
            $F.test.assertEquals("created absolutely last", this.getRowTitle(cmp, 3));
        }
    }
})