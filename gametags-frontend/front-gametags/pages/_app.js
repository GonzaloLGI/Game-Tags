import '@picocss/pico'
import '../styles/theme.css'

function MyApp({ Component, pageProps }) {
     return (
          <>
               <Component {...pageProps} />
               <style jsx global>{`
                    body {
                         }
               `}</style>
          </>)
}

export default MyApp