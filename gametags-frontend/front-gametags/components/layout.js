import Navbar from "./navbar"

export default function Layout({ children }) {
  return (
    <>
      <Navbar />
      <meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
      <main className="container" data-theme="light" margin-top="100px">{children}</main>
    </>
  )
}