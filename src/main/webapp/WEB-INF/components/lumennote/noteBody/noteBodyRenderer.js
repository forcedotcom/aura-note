({
	afterRender : function(cmp){
		var content = cmp.find("content").getElement();
		content.style.maxHeight = cmp.get("v.maxHeight");
		this.superAfterRender();
	}
})