/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "../resources/templates/**/*.html", // 타임리프(HTML) 파일 위치
    "../resources/static/js/**/*.js",    // JS 파일 위치
    "./input.css"                        // 현재 폴더의 CSS
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}