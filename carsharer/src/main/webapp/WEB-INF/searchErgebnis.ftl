<#include "view_Search.ftl">

<p>Suchergebnisse</p>

<div class="Boxed" style="max-height: 400px; overflow-y: auto;"> 
    <#list suchergebniss as su>
        <div class="Boxed" style="margin: 10px 0; padding: 10px; display: flex; align-items: center; gap: 20px;">
            
            <!-- Icon and Submit Button -->
            <form action="fahrtdetail" method="get" style="margin: 0;">
                <button name="fid" value="${su.fid}" type="submit" style="border: none; background: none; cursor: pointer;">
                    <img src="${su.icon1}" alt="Icon" style="height: 60px;" />
                </button>
            </form>

            <!-- Fahrt Info -->
            <div>
                <div><strong>Von:</strong> ${su.startOrt}</div>
                <div><strong>Nach:</strong> ${su.zielOrt}</div>
                <div><strong>Fahrdatum & Uhrzeit:</strong> ${su.fahrdatumunduhrzeit}</div>
                <div><strong>Fahrtkosten:</strong> ${su.fahrtkosten1}</div>
            </div>

        </div>
    </#list>
</div>
