import Layout from '../components/layout'
import {toJSON, fromJSON} from 'flatted';
import { useEffect, useCallback, useState } from 'react';
import { postRequest } from '../service/backendService';

export default function UploadVideogame() {

  const [videogames, setVideogame] = useState([]);

  function createClassification(entity, age, url,country){
    return {system:entity,tag:age,url:url,country:country}
  }

  function addPlatforms(platforms){
    const platformArray = []
    platforms.forEach(element => {
      if(document.getElementById(element).checked){
        console.log(document.getElementById(element).value)
        platformArray.push(document.getElementById(element).value)
      }
    });
    return platformArray
  }

  async function addVideogame(URL, videogame) {
    if(window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == undefined){
      window.location="http://localhost:3000/home"
      console.log("Sin credenciales")
      return
    }
    const createdVideogame = await postRequest(URL,videogame,window.localStorage.getItem("token"));
    setVideogame(createdVideogame)
    window.location="http://localhost:3000/search"
  }
  

  return (
    <Layout>
      <div>
        <form>
          <input type='text' id='name' placeholder='Write the videogame name' />
          <input type='text' id='developer' placeholder='Write the developer name' />
          <label for="platforms">Select the platforms where the videogame is available:</label>
          <input type="checkbox" id="platform1" name="platform1" value="PS4"/>
          <label for="platform1"> PS4</label>
          <input type="checkbox" id="platform2" name="platform2" value="Xbox One"/>
          <label for="platform2"> Xbox One</label>
          <input type="checkbox" id="platform3" name="platform3" value="PS3"/>
          <label for="platform3"> PS3</label>
          <input type="checkbox" id="platform4" name="platform4" value="Xbox 360"/>
          <label for="platform4"> Xbox 360</label>
          <div>
            <input type="text" id='entity' placeholder='Write the name of the classification entity' />
            <input type="text" id='age' placeholder='Write the age of the label' />
            <input type="text" id='url' placeholder='Write the URL home-page of the classification entity' />
            <input type="text" id='country' placeholder='Write the country from where the entity belongs' />
          </div>
        </form>
      </div>
      <div>
        <input type="button" value="Submit new game" onClick={e => addVideogame("/videogame/",
        {name:document.getElementById("name").value,developer:document.getElementById("developer").value,
        platforms:addPlatforms(["platform1","platform2","platform3","platform4"]),classifications:[
          createClassification(document.getElementById("entity").value,document.getElementById("age").value,document.getElementById("url").value,document.getElementById("country").value)]
          ,uploadUser:window.localStorage.getItem("userName")})} />
      </div>
    </Layout>
  )
}