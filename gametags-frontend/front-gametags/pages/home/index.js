import Layout from '../../components/layout'
import Link from 'next/link'
import { getRequest } from '../../service/backendService';
import { useEffect, useCallback, useState } from 'react';

export default function Home() {

  const [videogames, setVideogame] = useState([]);

  useEffect(() => {
    console.log("token: " + window.localStorage.getItem("token"))
    threeLatestAddedVideogames("/videogame/latest");
  }, [])

  async function threeLatestAddedVideogames(URL) {
    const videogame = await getRequest(URL,window.localStorage.getItem("token"), window.localStorage.getItem("token"));
    if (videogame.constructor == Array) {
      setVideogame(videogame)
    } else {
      setVideogame([videogame])
    }
  }

  return (
    <Layout>
      <div>
        <div>Ultimas incorporaciones</div>
        <div>
          {
            videogames.map((videogame, key) => {
              const name = videogame.name;
              return (
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
      </div>
    </Layout>
  )
}
