({
	provide : function(cmp) {
		return cmp.getAttributes().get("mode") === "view" ? "lumennote:noteView" : "lumennote:noteEdit";
	}
})