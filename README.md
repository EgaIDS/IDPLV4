# IDPLV4
setelah clone ubah config pada file application.properties

# sesuaikan port dengan ketersediaan port di masing2 perangkat
server.port = 9090

# sesuaikan config database
spring.datasource.url=jdbc:postgresql://localhost:5432/TrainingKaryawan
spring.datasource.username=postgres
spring.datasource.password=rahasia

# pastikan pada saat pertama kali menjalankan service, value config dibawah ini adalah "update" 
spring.jpa.hibernate.ddl-auto=update
