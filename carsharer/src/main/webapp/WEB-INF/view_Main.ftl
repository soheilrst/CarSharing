<#include "header.ftl">

<div class="slider">
    <div class="content">
        <div class="principal">
            <div class="center text">
                <label><span>Car Sharer</span></label>
            </div>

            <!-- Meine Reservierten Fahrten -->
            <p class="section-title">Meine reservierten Fahrten</p>
            <div class="Boxed">
                <table>
                    <thead>
                        <tr>
                            <th>Von</th>
                            <th>Nach</th>
                            <th>Status</th>
                            <th>Aktion</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list reservierteFahrten as fa>
                        <tr>
                            <td>${fa.von}</td>
                            <td>${fa.nach}</td>
                            <td>${fa.status}</td>
                            <td>
                                <form action="fahrtdetail" method="get">
                                    <button name="fid" value="${fa.fid}" class="details-btn">Details</button>
                                </form>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>

            <!-- Offene Fahrten -->
            <p class="section-title">Offene Fahrten</p>
            <div class="Boxed">
                <table>
                    <thead>
                        <tr>
                            <th>Startort</th>
                            <th>Zielort</th>
                            <th>Fahrtkosten</th>
                            <th>Freiplatz</th>
                            <th>Aktion</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list offeneFahrten as of>
                        <tr>
                            <td>${of.startort}</td>
                            <td>${of.zielort}</td>
                            <td>${of.fahrtkosten}</td>
                            <td>${of.freiplatz}</td>
                            <td>
                                <form action="fahrtdetail" method="get">
                                    <button name="fid" value="${of.fid}" class="details-btn">Details</button>
                                </form>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>

            <!-- Fahrt Erstellen Button -->
            <form action="newDrive" method="get">
                <input class="newride" type="submit" value="Fahrt Erstellen" />
            </form>
        </div>
    </div>
</div>

</body>
</html>
