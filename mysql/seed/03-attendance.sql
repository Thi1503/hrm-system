USE attendance_service;

SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM attendance_log WHERE employee_id = 4;
DELETE FROM attendance_daily_summary WHERE employee_id = 4;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO work_calendar (work_date, day_type, description) VALUES
                                                                 ('2025-12-01', 'WORKING', NULL),
                                                                 ('2025-12-02', 'WORKING', NULL),
                                                                 ('2025-12-03', 'WORKING', NULL),
                                                                 ('2025-12-04', 'WORKING', NULL),
                                                                 ('2025-12-05', 'WORKING', NULL),
                                                                 ('2025-12-06', 'WEEKEND', 'Saturday'),
                                                                 ('2025-12-07', 'WEEKEND', 'Sunday'),

                                                                 ('2025-12-08', 'WORKING', NULL),
                                                                 ('2025-12-09', 'WORKING', NULL),
                                                                 ('2025-12-10', 'WORKING', NULL),
                                                                 ('2025-12-11', 'WORKING', NULL),
                                                                 ('2025-12-12', 'WORKING', NULL),
                                                                 ('2025-12-13', 'WEEKEND', 'Saturday'),
                                                                 ('2025-12-14', 'WEEKEND', 'Sunday'),

                                                                 ('2025-12-15', 'WORKING', NULL),
                                                                 ('2025-12-16', 'WORKING', NULL),
                                                                 ('2025-12-17', 'WORKING', NULL),
                                                                 ('2025-12-18', 'WORKING', NULL),
                                                                 ('2025-12-19', 'WORKING', NULL),
                                                                 ('2025-12-20', 'WEEKEND', 'Saturday'),
                                                                 ('2025-12-21', 'WEEKEND', 'Sunday'),

                                                                 ('2025-12-22', 'WORKING', NULL),
                                                                 ('2025-12-23', 'WORKING', NULL),
                                                                 ('2025-12-24', 'WORKING', NULL),
                                                                 ('2025-12-25', 'WORKING', 'Christmas (optional)'),
                                                                 ('2025-12-26', 'WORKING', NULL),
                                                                 ('2025-12-27', 'WEEKEND', 'Saturday'),
                                                                 ('2025-12-28', 'WEEKEND', 'Sunday'),

                                                                 ('2025-12-29', 'WORKING', NULL),
                                                                 ('2025-12-30', 'WORKING', NULL),
                                                                 ('2025-12-31', 'WORKING', NULL);


INSERT INTO work_calendar (work_date, day_type, description) VALUES
                                                                 ('2026-01-01', 'HOLIDAY', 'Tết Dương lịch'),
                                                                 ('2026-01-02', 'WORKING', NULL),
                                                                 ('2026-01-03', 'WEEKEND', 'Saturday'),
                                                                 ('2026-01-04', 'WEEKEND', 'Sunday'),

                                                                 ('2026-01-05', 'WORKING', NULL),
                                                                 ('2026-01-06', 'WORKING', NULL),
                                                                 ('2026-01-07', 'WORKING', NULL),
                                                                 ('2026-01-08', 'WORKING', NULL),
                                                                 ('2026-01-09', 'WORKING', NULL),
                                                                 ('2026-01-10', 'WEEKEND', 'Saturday'),
                                                                 ('2026-01-11', 'WEEKEND', 'Sunday'),

                                                                 ('2026-01-12', 'WORKING', NULL),
                                                                 ('2026-01-13', 'WORKING', NULL),
                                                                 ('2026-01-14', 'WORKING', NULL),
                                                                 ('2026-01-15', 'WORKING', NULL),
                                                                 ('2026-01-16', 'WORKING', NULL),
                                                                 ('2026-01-17', 'WEEKEND', 'Saturday'),
                                                                 ('2026-01-18', 'WEEKEND', 'Sunday'),

                                                                 ('2026-01-19', 'WORKING', NULL),
                                                                 ('2026-01-20', 'WORKING', NULL),
                                                                 ('2026-01-21', 'WORKING', NULL),
                                                                 ('2026-01-22', 'WORKING', NULL),
                                                                 ('2026-01-23', 'WORKING', NULL),
                                                                 ('2026-01-24', 'WEEKEND', 'Saturday'),
                                                                 ('2026-01-25', 'WEEKEND', 'Sunday'),

                                                                 ('2026-01-26', 'WORKING', NULL),
                                                                 ('2026-01-27', 'WORKING', NULL),
                                                                 ('2026-01-28', 'WORKING', NULL),
                                                                 ('2026-01-29', 'WORKING', NULL),
                                                                 ('2026-01-30', 'WORKING', NULL),
                                                                 ('2026-01-31', 'WEEKEND', 'Saturday');

-- 1. LOCATION RULE
INSERT INTO attendance_location_rule
(id, name, latitude, longitude, radius_meter, is_active)
VALUES
    (1, 'HN Office', 21.028511, 105.804817, 200, TRUE);

-- 2. SHIFT
INSERT INTO attendance_shift
(id, name, start_time, end_time, late_threshold_min, early_threshold_min)
VALUES
    (1, 'HC', '08:30:00', '17:30:00', 5, 5);

-- 3. ATTENDANCE LOG (OK)
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-01 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-01 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());
-- =============================
-- 2025-12-01 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-01 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-01 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-01',1,'2025-12-01 08:28:00','2025-12-01 17:30:00',0,0,540,'NORMAL',NOW());

-- =============================
-- 2025-12-02 LATE
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-02 08:40:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-02 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-02',1,'2025-12-02 08:40:00','2025-12-02 17:30:00',10,0,520,'LATE',NOW());

-- =============================
-- 2025-12-03 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-03 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-03 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-03',1,'2025-12-03 08:28:00','2025-12-03 17:30:00',0,0,540,'NORMAL',NOW());

-- =============================
-- 2025-12-04 ABSENT
-- =============================
INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-04',1,NULL,NULL,0,0,0,'ABSENT',NOW());

-- =============================
-- 2025-12-05 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-05 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-05 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-05',1,'2025-12-05 08:28:00','2025-12-05 17:30:00',0,0,540,'NORMAL',NOW());

-- 12-06, 12-07 WEEKEND (skip)

-- =============================
-- 2025-12-08 LATE
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-08 08:40:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-08 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-08',1,'2025-12-08 08:40:00','2025-12-08 17:30:00',10,0,520,'LATE',NOW());

-- =============================
-- 2025-12-09 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-09 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-09 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-09',1,'2025-12-09 08:28:00','2025-12-09 17:30:00',0,0,540,'NORMAL',NOW());

-- =============================
-- 2025-12-10 LATE
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-10 08:40:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-10 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-10',1,'2025-12-10 08:40:00','2025-12-10 17:30:00',10,0,520,'LATE',NOW());

-- =============================
-- 2025-12-11 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-11 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-11 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-11',1,'2025-12-11 08:28:00','2025-12-11 17:30:00',0,0,540,'NORMAL',NOW());

-- =============================
-- 2025-12-12 LATE
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-12 08:40:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-12 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-12',1,'2025-12-12 08:40:00','2025-12-12 17:30:00',10,0,520,'LATE',NOW());

-- 12-13, 12-14 WEEKEND

-- =============================
-- 2025-12-15 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-15 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-15 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-15',1,'2025-12-15 08:28:00','2025-12-15 17:30:00',0,0,540,'NORMAL',NOW());

-- =============================
-- 2025-12-16 LATE
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-16 08:40:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-16 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-16',1,'2025-12-16 08:40:00','2025-12-16 17:30:00',10,0,520,'LATE',NOW());

-- =============================
-- 2025-12-17 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-17 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-17 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-17',1,'2025-12-17 08:28:00','2025-12-17 17:30:00',0,0,540,'NORMAL',NOW());

-- =============================
-- 2025-12-18 ABSENT
-- =============================
INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-18',1,NULL,NULL,0,0,0,'ABSENT',NOW());

-- =============================
-- 2025-12-19 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-19 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-19 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-19',1,'2025-12-19 08:28:00','2025-12-19 17:30:00',0,0,540,'NORMAL',NOW());

-- 12-20, 12-21 WEEKEND

-- =============================
-- 2025-12-22 LATE
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-22 08:40:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-22 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-22',1,'2025-12-22 08:40:00','2025-12-22 17:30:00',10,0,520,'LATE',NOW());

-- =============================
-- 2025-12-23 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-23 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-23 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-23',1,'2025-12-23 08:28:00','2025-12-23 17:30:00',0,0,540,'NORMAL',NOW());

-- =============================
-- 2025-12-24 LATE
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-24 08:40:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-24 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-24',1,'2025-12-24 08:40:00','2025-12-24 17:30:00',10,0,520,'LATE',NOW());

-- =============================
-- 2025-12-25 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-25 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-25 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-25',1,'2025-12-25 08:28:00','2025-12-25 17:30:00',0,0,540,'NORMAL',NOW());

-- =============================
-- 2025-12-26 LATE
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-26 08:40:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-26 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-26',1,'2025-12-26 08:40:00','2025-12-26 17:30:00',10,0,520,'LATE',NOW());

-- 12-27, 12-28 WEEKEND

-- =============================
-- 2025-12-29 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-29 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-29 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-29',1,'2025-12-29 08:28:00','2025-12-29 17:30:00',0,0,540,'NORMAL',NOW());

-- =============================
-- 2025-12-30 LATE
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-30 08:40:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-30 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-30',1,'2025-12-30 08:40:00','2025-12-30 17:30:00',10,0,520,'LATE',NOW());

-- =============================
-- 2025-12-31 NORMAL
-- =============================
INSERT INTO attendance_log VALUES
                               (NULL,4,'2025-12-31 08:28:00','IN',21.0285,105.8048,1,TRUE,'Android',NOW()),
                               (NULL,4,'2025-12-31 17:30:00','OUT',21.0285,105.8048,1,TRUE,'Android',NOW());

INSERT INTO attendance_daily_summary
VALUES (NULL,4,'2025-12-31',1,'2025-12-31 08:28:00','2025-12-31 17:30:00',0,0,540,'NORMAL',NOW());



