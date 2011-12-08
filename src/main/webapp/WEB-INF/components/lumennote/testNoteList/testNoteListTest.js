({
    getRowTitle : function(cmp, index) {
        return cmp.find("list").find("row")[index].getSuper().get("v.body")[0].getElement().textContent;
    },

    tearDown : function(cmp) {
        var a = cmp.get("c.deleteNotesByKey");
        a.setParams({ key : cmp.get("m.key") });
        $F.test.callServerAction(a);
    }
})