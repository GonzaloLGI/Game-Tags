import { getRequest } from '../service/backendService';
import Link from 'next/link'
import Layout from '../components/layout'
import { useEffect, useCallback, useState } from 'react';
import { useSearchParams } from 'next/navigation';
import { useRouter } from 'next/router';

export default function selectedVideogame() {
  const URL = "/videogame/name/" + useSearchParams().get('name');
  const [videogames, setVideogame] = useState([]);
  const platforms = videogames.platforms;

  useEffect(() => {
    getVideogame(URL);
  }, [])

  async function getVideogame(URL) {
    console.log(window.localStorage.getItem("name"))
    if (URL.endsWith("undefined") || URL.endsWith("null")) {
      URL = "/videogame/name/" + window.localStorage.getItem("name");
    }
    const videogame = await getRequest(URL,window.localStorage.getItem("token"), window.localStorage.getItem("token"));
    if (videogame.name != undefined) {
      window.localStorage.setItem("name", videogame.name)
    }
    setVideogame(videogame)
  }

  function showClassifications(videogames) {
    if (videogames != undefined && videogames.classifications != undefined) {
      return videogames.classifications.map((element, key) => {
        return (
          <ul>
            <li>
              Entidad: {element.system}
            </li>
            <li>
              Edad: {element.tag}
            </li>
            <li>
              Pais: {element.country}
            </li>
            <li>
              Enlace: {element.url}
            </li>
          </ul>
        )
      })
    }
  };

  function showPlatforms(videogames) {
    if (videogames != undefined && videogames.platforms != undefined) {
      return videogames.platforms.map((element, key) => {
        return (
          <ul>
            <li>{element}</li>
          </ul>
        )
      })
    }
  }

  function showComments(videogames,category){
    if (videogames != undefined && videogames.platforms != undefined) {
      return videogames.comments.map((comment, key) => {
        if(comment.category == category){
          return (
            <ul>
              <li>
                Severidad: {comment.severity}
              </li>
              <li>
                Texto: {comment.text}
              </li>
              <li>
                Usuario: {comment.uploadUser}
              </li>
              <li>
                Fecha de escritura: {comment.uploadDate}
              </li>
            </ul>
          )
        }
      })
    }
  }

  return (
    <Layout>
      <ul>
        <li>
          <Link href={{
            pathname: "/newClassification",
            query: {
              id: videogames.id,
              name: videogames.name
            }
          }}>Add new classification</Link>
        </li>
        <li>
          <Link href={{
            pathname: "/newComment",
            query: {
              id: videogames.id,
              name: videogames.name
            }
          }}>Add new comment</Link>
        </li>
        <li>
          total videogames: {videogames.length}
          <div>
            <ul>
              <li>
                Nombre: {videogames.name}
              </li>
              <li>
                Desarrollador: {videogames.developer}
              </li>
              <li>
                Etiquetas de edad:
                <div>
                  {
                    showClassifications(videogames)
                  }
                </div>
              </li>
              <li>
                Plataformas:
                <div>
                  {
                    showPlatforms(videogames)
                  }
                </div>
              </li>
              <li>
                Fecha de subida: {videogames.uploadDateTime}
              </li>
              <li>
                Agregado por: {videogames.uploadUser}
              </li>
              <li>
                Comentarios de violencia:
                {
                  showComments(videogames, "Violence")
                }
              </li>
              <li>
                Comentarios de lenguaje:
                {
                  showComments(videogames, "Language")
                }
              </li>
              <li>
                Comentarios de sexualidad:
                {
                  showComments(videogames, "Sexuality")
                }
              </li>
              <li>
                Comentarios de drogas:
                {
                  showComments(videogames, "Drugs")
                }
              </li>
              <li>
                Comentarios de miscelanea:
                {
                  showComments(videogames, "Misc")
                }
              </li>
            </ul>
          </div>
        </li>
      </ul>
    </Layout>
  )
}