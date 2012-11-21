<aura:application preload="auranote" template="auranote:template" useAppCache="true">
	<div>
		<header>
			<h1>aura:note</h1>
		</header>
		<ui:block class="wrapper" aura:id="block">
			<aura:set attribute="left">
				<auranote:sidebar aura:id="sidebar" />
			</aura:set>
			<auranote:details aura:id="details" />
		</ui:block>
	</div>
</aura:application>
