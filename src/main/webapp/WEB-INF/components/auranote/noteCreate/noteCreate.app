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
<!-- 
    This app is used by the Auranote Web Clipper chrome extension. See README for instructions on installation and use.
-->
<aura:application preload="auranote" template="auranote:template" implements="auranote:noteData">
	<aura:attribute name="message" type="String"/>
	
	<aura:handler event="auranote:noteAdded" action="{!c.finish}" />
	
	<div>
		{!v.message}
		
		<auranote:details aura:id="detail" url="{!v.url}" image="{!v.image}" text="{!v.text}"/>
	</div>
</aura:application>
