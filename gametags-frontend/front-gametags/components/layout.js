import Navbar from "./navbar"

export default function Layout({ children }) {
  return (
    <>
      <Navbar />
      <main className="container" data-theme="light" margin-top="100px">{children}</main>
    </>
  )
}