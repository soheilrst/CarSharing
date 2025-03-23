<#include "header.ftl">
<div class="slider">
    <div class="content">
        <div class="principal">
            <!-- Information Section -->
            <div class="Boxed">
                <h2 class="section-title">Information</h2>
                <#list FaherObj as fo>
                    <div class="info-container">
                        <p><strong>Anbieter:</strong> ${fo.anbieter}</p>
                        <p><strong>Fahrdatum & Uhrzeit:</strong> ${fo.fahrdatumunduhrzeit}</p>
                        <p><strong>Startort:</strong> ${fo.von}</p>
                        <p><strong>Zielort:</strong> ${fo.nach}</p>
                        <p><strong>Freie Plätze:</strong> ${fo.anzahlfreieplaetze}</p>
                        <p><strong>Fahrtkosten:</strong> ${fo.fahrtkosten} €</p>
                        <p><strong>Status:</strong> ${fo.status}</p>
                        <p><strong>Beschreibung:</strong> ${fo.beschreibung}</p>
                    </div>
                </#list>
            </div>

            <!-- Action Section -->
            <div class="Boxed">
                <h3 class="section-title">Aktionsliste</h3>
                <form action="fahrtdetail" method="POST">
                    <label for="anzahl">Anzahl der Plätze auswählen:</label>
                    <input type="number" min="1" max="2" step="1" name="anzahl" value="1">
                    <button class="action-btn" type="submit" name="fahrtTry" value="Fahrtreservieren">Fahrt reservieren</button>
                    <button class="action-btn delete" type="submit" name="fahrtTry" value="FahrtLöschen">Fahrt löschen</button>
                </form>
            </div>

            <!-- Rating Section -->
            <div class="Boxed">
                <h3 class="section-title">Bewertung</h3>
                <p><strong>Durchschnitt:</strong> ${durchschnitt}</p>
                <table>
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Kommentar</th>
                            <th>Bewertung</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list Rating as r>
                        <tr>
                            <td>${r.email}</td>
                            <td class="comment-cell">${r.textnachricht}</td>
                            <td>${r.rating}</td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>

            <!-- Ratings Button -->
            <form method="POST" action="fahrtdetail">
                <button class="ratings-btn" type="submit" name="fahrtTry" value="ratings">Bewertung abgeben</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
