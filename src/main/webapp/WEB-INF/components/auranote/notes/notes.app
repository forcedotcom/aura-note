<!--

    Copyright (C) 2014 salesforce.com, inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
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
