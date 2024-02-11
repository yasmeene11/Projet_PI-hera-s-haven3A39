<?php

namespace App\Repository;

use App\Entity\Boarding;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Boarding>
 *
 * @method Boarding|null find($id, $lockMode = null, $lockVersion = null)
 * @method Boarding|null findOneBy(array $criteria, array $orderBy = null)
 * @method Boarding[]    findAll()
 * @method Boarding[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class BoardingRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Boarding::class);
    }

//    /**
//     * @return Boarding[] Returns an array of Boarding objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('b')
//            ->andWhere('b.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('b.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Boarding
//    {
//        return $this->createQueryBuilder('b')
//            ->andWhere('b.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
