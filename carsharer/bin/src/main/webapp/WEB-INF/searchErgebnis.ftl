<#include "view_Search.ftl">
					<p>Suchergebnisse</p>
					<div class="Boxed" style="height: 400;"> 
						<#list suchergebniss as su>
		                    <div class="Boxed"> 
											<tr class= "container">
												<form action="fahrtdetail" method="get">
													<td><button name = "fid" placeholder="Fid" value =${su.fid} type="submit" style="display:block;margin-left:auto;margin-right:auto;"><image src="${su.icon1}" style="height:60;"/></button></td>
												</form>
												
												<div><td><label>Von<pre> ${su.startOrt}</pre></label></td></div>
												<div><td><label>Nach<pre> ${su.zielOrt}</pre></label></td></div>
												<div><td><label>fahrtkosten<pre> ${su.fahrtkosten1}</pre></label></td></div>
											</tr>
								
		                    </div>
		                  </#list>
		                </div> 
					</div>
				</div>
			</div>