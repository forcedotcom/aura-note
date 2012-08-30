<plume:application preload="plumenote" template="plumenote:template" useAppCache="true">
	<div>
		<header>
			<h1>plume:note</h1>
		</header>
		<ui:block class="wrapper" plume:id="block">
			<plume:set attribute="left">
				<plumenote:sidebar plume:id="sidebar" />
			</plume:set>
			<plumenote:details plume:id="details" />
		</ui:block>
	</div>
</plume:application>
