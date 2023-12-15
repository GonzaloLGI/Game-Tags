import Layout from '../components/layout'
import { useEffect, useCallback, useState } from 'react';
import { putRequest } from '../service/backendService';
import { useSearchParams } from 'next/navigation';

export default function NewClassification() {
  
  const [classification, setClassification] = useState([]);

  async function addClassification(URL, classification) {
    if(window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == undefined){
      window.location="http://localhost:3000/home"
      console.log("Sin credenciales")
      return
    }
    const createdClassification = await putRequest(URL,classification,window.localStorage.getItem("token"),window.localStorage.getItem("token"));
    setClassification(createdClassification)
    window.location="http://localhost:3000/search"
  }

  console.log(useSearchParams().get('name'))
  const videogameName= useSearchParams().get('name')

  return (
    <Layout>
      <div> 
        <form>
          <input type='text' id='entity' placeholder='Write entity name'/>
          <input type='text' id='age' placeholder='Write age of the label'/>
          <input type='text' id="url" placeholder='Write URL of the homepage of the entity'/>
          <input type='text' id='country' placeholder='Write country where the entity belongs'/>
          <input type='button' value="Add new classification" onClick={e => addClassification("/videogame/classification/"+videogameName,{system:document.getElementById("entity").value,
          country:document.getElementById("country").value,tag:document.getElementById("age").value,url:document.getElementById("url").value})}/>
        </form>
      </div>
    </Layout>
  )
}