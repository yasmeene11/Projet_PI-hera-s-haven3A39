<?php

namespace App\Repository;

use App\Entity\DonationP;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<DonationP>
 *
 * @method DonationP|null find($id, $lockMode = null, $lockVersion = null)
 * @method DonationP|null findOneBy(array $criteria, array $orderBy = null)
 * @method DonationP[]    findAll()
 * @method DonationP[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class DonationPRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, DonationP::class);
    }

//    /**
//     * @return DonationP[] Returns an array of DonationP objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('d')
//            ->andWhere('d.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('d.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?DonationP
//    {
//        return $this->createQueryBuilder('d')
//            ->andWhere('d.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
public function findByProductName($donation_product_name)
{
    return $this->createQueryBuilder('d')
        ->andWhere('d.donation_product_name LIKE :donation_product_name')
        ->orWhere('d.donation_product_quantity LIKE :donation_product_name')
        ->orWhere('d.donation_product_label LIKE :donation_product_name')

        ->orWhere('d.donationP_type LIKE :donation_product_name')
        ->setParameter('donation_product_name', '%' . $donation_product_name . '%')
        ->getQuery()
        ->getResult();
}
}