Q1:

SELECT h.*, t.* FROM habitaciones h
INNER JOIN tipos_habitaciones t ON h.tipos_habitaciones_tip_hab_id = t.tip_hab_id
WHERE tip_hab_tipo = "INDIVIDUAL"
AND h.hab_id NOT IN (SELECT DISTINCT r.habitaciones_hab_id FROM reservas r
WHERE ((r.res_fecha_inicio >= "2017-06-8" AND r.res_fecha_inicio < "2017-06-13")
OR (r.res_fecha_inicio < "2017-06-8" AND r.res_fecha_final > "2017-06-8"))
AND (r.res_estado = "ACTIVA" OR r.res_estado = "PROGRESA")
) ORDER BY h.hab_numero


//la consulta que lanzamos después da la busqueda de habitaciones y antes de acabar la reserva,
//en caso que si en eso rato por otro cliente ya había reservado ésta habitación mientras la reservamos nosotros

SELECT r.res_id FROM reservas r
WHERE habitaciones_hab_id = "27"
AND ((r.res_fecha_inicio >= "2017-06-12" AND r.res_fecha_inicio < "2017-06-22")
OR (r.res_fecha_inicio < "2017-06-12" AND r.res_fecha_final > "2017-06-12"))
AND (r.res_estado = "ACTIVA" OR r.res_estado = "PROGRESA") LIMIT 1


Q2:

SELECT * FROM clientes c
INNER JOIN clientes_habituales h
ON c.cli_id = h.cli_id
WHERE c.cli_dni = '12345678D';

Q3:

SELECT * FROM reservas
WHERE (res_estado = 'ACTIVA' OR (res_estado = 'CERRADA' AND res_fecha_final BETWEEN DATE_SUB(NOW(), INTERVAL 2 YEAR) AND NOW())) AND clientes_cli_id = '4';

Q4:

SELECT * FROM bdweb_hotel.reservas
WHERE res_estado = 'ACTIVA' AND res_fecha_inicio > DATE_ADD(NOW(), INTERVAL 2 DAY) AND clientes_cli_id='4';

Q5:

SELECT * FROM bdweb_hotel.promociones;

Q6:

SELECT * FROM bdweb_hotel.reservas
WHERE res_fecha_inicio = CURDATE() AND res_estado = 'ACTIVA';

Q7:

SELECT t.tip_hab_tipo, COUNT(*) FROM habitaciones h
INNER JOIN tipos_habitaciones t ON h.tipos_habitaciones_tip_hab_id = t.tip_hab_id
WHERE h.hab_id NOT IN (SELECT DISTINCT r.habitaciones_hab_id FROM reservas r
WHERE ((r.res_fecha_inicio = "2017-07-9")
OR (r.res_fecha_inicio < "2017-06-9" AND r.res_fecha_final > "2017-07-9"))
AND (r.res_estado = "ACTIVA" OR r.res_estado = "PROGRESA")
) group by t.tip_hab_id;

Q8:

SELECT SUM(res_importe) FROM bdweb_hotel.reservas
WHERE MONTH(res_fecha_final) = 5 AND YEAR(res_fecha_final) = 2017 AND res_estado = "CERRADA";

Q9:

SELECT * FROM bdweb_hotel.reservas
WHERE res_fecha_inicio >= CURDATE()
AND res_estado = 'ACTIVA';

_____________________________________________________________________________________________________________________________________

I1:

BEGIN;
INSERT INTO clientes (cli_nombre,cli_apellidos,cli_password,cli_direccion,cli_dni,cli_email,cli_numero_movil,cli_numero_tarjeta)
VALUES ("Marcos","Sanchez","a8e63c27e6f7f57104773b2765e2de02851f216657235ce7b264f13978f3f3cd4ee2e4cf10d7c7643222e7551e6c6b2da0656dcf97b22dfc04b4c19046843501","Valencia, Calle Sorolla","12345677A","msanchez@gmail.com","555555555","1234123412341234");
INSERT INTO clientes_habituales (cli_id,cli_newsletter,cli_descuento)  VALUES(LAST_INSERT_ID(),"10","1");
COMMIT;

I2:

UPDATE bdweb_hotel.clientes_habituales
SET cli_descuento = '10';