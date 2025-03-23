<#include "header.ftl">

<div class="slider">
    <div class="content">
        <div class="principal">
            <div class="center text"> 
                <label>Fahrt Erstellen</label> 
            </div>
            <form action="newDrive" method="post">
                <fieldset id="fahrterstellen">
                    <div>
                        <label>Von:</label>  
                        <input type="text" name="von" list="cityList" required oninput="capitalizeFirstLetter(this)">
                    </div>

                    <div>
                        <label>Bis:</label>  
                        <input type="text" name="bis" list="cityList" required oninput="capitalizeFirstLetter(this)">
                    </div>

                    <div> 
                        <label>Kapazität:</label>  
                        <input type="text" name="maxkap" required>
                    </div>

                    <div> 
                        <label>Fahrkosten:</label>  
                        <input type="text" name="kosten" required>
                        <select name="payment">
                            <option value="Euro">Euro</option>
                            <option value="Dolar">Dolar</option>
                        </select>
                    </div>

                    <div> 
                        <label>Transportmittel:</label>
                        <div class="rb">
                        	<br></br>
                            <div> <input type="radio" name="Transportmittel" value="1" checked> Auto</div>
                            <div> <input type="radio" name="Transportmittel" value="2"> Bus</div>
                            <div> <input type="radio" name="Transportmittel" value="3"> Kleintransporter</div>
                        </div>
                    </div>

                    <div>
                        <label>Fahrzeit:</label>
                        <input type="date" id="datum" name="datum" required>
                        <input type="time" id="appt" name="time" required>
                    </div>

                    <div>
                        <label>Beschreibung:</label>
                        <textarea rows="4" name="beschreibung" maxlength="50"></textarea>
                    </div>
                </fieldset>
                <input class="erstellen" type="submit" value="Fahrt Erstellen">
            </form>
        </div>
    </div>
</div>

<datalist id="cityList">
    <option value="Berlin">
    <option value="München">
    <option value="Hamburg">
    <option value="Köln">
    <option value="Frankfurt">
    <option value="Stuttgart">
    <option value="Düsseldorf">
    <option value="Dortmund">
    <option value="Essen">
    <option value="Bremen">
</datalist>

<script>
document.addEventListener("DOMContentLoaded", function() {
    let today = new Date().toISOString().split('T')[0];
    document.getElementById("datum").setAttribute("min", today);
});

function capitalizeFirstLetter(input) {
    input.value = input.value.charAt(0).toUpperCase() + input.value.slice(1);
}

document.getElementById("appt").addEventListener("input", function() {
    let now = new Date();
    let selectedDate = document.getElementById("datum").value;
    let selectedTime = this.value;
    
    let selectedDateTime = new Date(selectedDate + 'T' + selectedTime);
    if (selectedDateTime < now) {
        alert("Die gewählte Zeit muss in der Zukunft liegen.");
        this.value = "";
    }
});
</script>
