<?php

namespace App\Form;

use App\Entity\Adoption;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Entity\Account;
use App\Entity\Animal;
use Symfony\Component\Form\FormInterface;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;



class AdoptionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder->add('Adoption_Date');

        if ($options['is_admin']) {
            $builder
                ->add('Adoption_Status', ChoiceType::class, [
                    'choices' => [
                        'Cancelled' => 'Cancelled',
                        'Pending' => 'Pending',
                        'Adopted' => 'Adopted',
                    ],
                    'placeholder' => 'Select Status',
                    'required' => true,
                ])
                ->add('Adoption_Fee')
                ->add('Account_Key', EntityType::class, [
                    'class' => Account::class, 
                    'choice_label' => 'name',
                    'placeholder' => 'Select Account',
                    'required' => true,
                ])
                ->add('Animal_Key', EntityType::class, [
                    'class' => Animal::class, 
                    'choice_label' => 'Animal_Image',
                    'placeholder' => 'Select Animal',
                    'required' => true,
                ]);
        } else {
            // For the front office, set fields as not mapped or use HiddenType
            $builder
                ->add('Adoption_Status', HiddenType::class, [
                    'mapped' => false,
                ])
                ->add('Adoption_Fee', HiddenType::class, [
                    'mapped' => false,
                ])
                ->add('Account_Key', HiddenType::class, [
                    'mapped' => false,
                ])
                ->add('Animal_Key', HiddenType::class, [
                    'mapped' => false,
                ]);
        }
    }
    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Adoption::class,
            // Add a custom option to pass the role/context information
            'is_admin' => false,
            // Specify validation group based on the context
            'validation_groups' => function (FormInterface $form) {
                $groups = ['Default'];

                if ($form->getConfig()->getOption('is_admin')) {
                    // Add an additional validation group for the admin context
                    $groups[] = 'admin';
                }

                return $groups;
            },
        ]);
    }
}
