({
	afterRender : function(cmp){
		var iframe = cmp.find("iframe").getElement();
		iframe.style.maxHeight=cmp.get("v.maxHeight");
		iframe.contentDocument.body.innerHTML = cmp.get("v.value");
		this.superAfterRender();
	}
})