import Link from 'next/link'
import Layout from '../components/layout'
import { useEffect, useCallback, useState } from 'react';
import { getRequest } from '../service/backendService';
import { postRequest } from '../service/backendService';

export default function Search() {

  const [videogames, setVideogame] = useState([]);

  async function searchVideogame(URL){
    const videogame = await getRequest(URL, window.localStorage.getItem("token"));
    if(videogame.constructor == Array){
      setVideogame(videogame)
    }else{
      setVideogame([videogame])
    }
    
  }

  async function searchVideogameByPlatforms(URL, platforms) {
    const videogame = await postRequest(URL,platforms,window.localStorage.getItem("token"));
    if(videogame.constructor == Array){
      setVideogame(videogame)
    }else{
      setVideogame([videogame])
    }
  }

  return (
    <Layout>
      <form>
        <div>
          <input type="search" id='name' placeholder='Write videogame name' />
          <input type="button" value="Search by name" onClick={e => searchVideogame("/videogame/name/"+document.getElementById("name").value)}/>
        </div>
      </form>
      <form>
        <div>
          <label for="age">Select an age to filter:</label>
          <select name="age" id="age">
            <option value="3">3</option>
            <option value="7">7</option>
            <option value="12">12</option>
            <option value="16">16</option>
            <option value="18">18</option>
          </select>
          <input type="button" value="Search by age" onClick={e => searchVideogame("/videogame/tag/"+document.getElementById("age").value)}/>
        </div>
        <div>
          <label for="platform">Select a platform to filter:</label>
          <select name="platform" id="platform">
            <option value="Xbox 360">Xbox 360</option>
            <option value="PS3">PS3</option>
            <option value="Xbox One">Xbox One</option>
            <option value="PS4">PS4</option>
          </select>
          <input type="button" value="Search by platform" onClick={e => searchVideogameByPlatforms("/videogame/platforms",[document.getElementById("platform").value])}/>
        </div>
        <div>
          <label for="system">Select an system to filter:</label>
          <select name="system" id="system">
            <option value="PEGI">PEGI</option>
            <option value="ESRB">ESRB</option>
            <option value="BBFC">BBFC</option>
            <option value="USK">USK</option>
          </select>
          <input type="button" value="Search by system" onClick={e => searchVideogame("/videogame/system/"+document.getElementById("system").value)}/>
        </div>
        <div>
          <input type="search" id='developer' placeholder='Write developer name' />
          <input type="button" value="Search by developer" onClick={e => searchVideogame("/videogame/developer/"+document.getElementById("developer").value)}/>
        </div>
      </form>
      <ul>
        <li>
          Juegos encontrados: 
          <div>
            {
                videogames.map((videogame, key) => {
                  const name = videogame.name;
                  return(
                    <ul>
                      <li>
                      <Link href={{
                        pathname:"/selectedVideogame",
                        query: {
                          name: name
                        }
                      }}
                      >{videogame.name}</Link>
                      </li>
                    </ul>
                  )
                })
            }
          </div>
        </li>
      </ul>
    </Layout>
  )
}